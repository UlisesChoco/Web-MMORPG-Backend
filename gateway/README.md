# Gateway API

API gateway en NestJS para el backend de Web MMORPG. Este servicio expone una capa HTTP/REST y reenvía las operaciones a microservicios gRPC internos de auth, clases, combate, inventario, loot, jugador, torre y mundo.

## Qué hace este gateway

- Centraliza el acceso HTTP a los microservicios del juego.
- Convierte respuestas gRPC a DTOs HTTP más amigables.
- Aplica autenticación basada en cookie JWT para todas las rutas, excepto `POST /auth/login` y `POST /auth/register`.
- Habilita CORS con `credentials: true` para permitir cookies desde el frontend.

## Requisitos

- Node.js 20 o superior.
- Variables de entorno:
	- `JWT_SECRET`: secreto usado para verificar y firmar la cookie JWT.
	- `PORT` opcional, por defecto `3000`.

El repositorio incluye `.env-template` con un valor de ejemplo para `JWT_SECRET`.

## Ejecución local

```bash
npm install
npm run start:dev
```

Compilación y ejecución en modo producción:

```bash
npm run build
npm run start:prod
```

## Autenticación

El middleware global lee la cookie `auth_jwt`, la verifica con `JWT_SECRET`, inyecta el token en el header `Authorization: Bearer <token>` y rechaza la request con `401` si falta o es inválido.

Flujo de login:

1. `POST /auth/login` devuelve un JWT desde el microservicio Auth.
2. El gateway valida ese JWT y guarda la cookie `auth_jwt` con `httpOnly`, `sameSite=lax` y `secure=true` en producción.
3. El resto de endpoints usa esa cookie para autorizarse.

## Códigos HTTP y errores

Los endpoints usan principalmente respuestas `200 OK`, salvo `POST /auth/register`, que devuelve `201 Created`.

Cuando un microservicio responde con error gRPC, el gateway lo traduce así:

| Código gRPC | HTTP |
| --- | --- |
| `INVALID_ARGUMENT` | `400 Bad Request` |
| `NOT_FOUND` | `404 Not Found` |
| `ALREADY_EXISTS` | `409 Conflict` |
| `PERMISSION_DENIED` | `403 Forbidden` |
| `INTERNAL` | `503 Service Unavailable` |
| `UNAUTHENTICATED` | `401 Unauthorized` |
| otro | `500 Internal Server Error` |

### Cuándo aparece cada código HTTP

- `200 OK`: cuando la petición se procesa correctamente y el gateway puede mapear la respuesta del microservicio. En endpoints de listado, también se usa aunque la colección venga vacía.
- `201 Created`: solo en `POST /auth/register`, cuando el usuario queda creado en Auth.
- `400 Bad Request`: cuando el body, query params o path params tienen datos faltantes, inválidos o mal tipados. Ejemplos: IDs no numéricos, booleanos mal formados, o payloads incompletos en `update`/`add-item`/`equip-item`.
- `401 Unauthorized`: cuando falta la cookie `auth_jwt`, la cookie es inválida, está expirada o el login devuelve un JWT no verificable. También cubre credenciales inválidas en `POST /auth/login`.
- `404 Not Found`: cuando el recurso pedido no existe en el microservicio. Ejemplos: clase, jugador, inventario, mapa, enemigo, NPC, piso de torre o historial asociado a un ID inexistente.
- `409 Conflict`: cuando la operación choca con el estado actual. Ejemplos: email ya registrado, jugador ya existente, item ya equipado, progreso de torre duplicado o cualquier conflicto de negocio que el microservicio reporte así.
- `500 Internal Server Error`: fallback para errores no tipados o no mapeados por el gateway.
- `503 Service Unavailable`: cuando el microservicio gRPC responde `INTERNAL` o no se pudo completar la llamada correctamente por caída, indisponibilidad o error interno del servicio remoto.

Regla práctica para el frontend: si la ruta devuelve un recurso único por ID, `200` significa que lo podés mapear directamente; `404` significa que ese recurso no existe; `503` significa que el backend remoto falló y conviene mostrar un error de disponibilidad o reintentar.

## Endpoints

### Auth

#### POST /auth/login

Request

- Body JSON: `{ email: string, password: string }`

Response

```json
{
	"message": "string",
	"jwt": "string"
}
```

Además, el gateway crea la cookie `auth_jwt` con el JWT devuelto por Auth.

Estados

- `200`: las credenciales son válidas, el JWT se verifica correctamente y la cookie `auth_jwt` queda seteada.
- `400`: el body llega incompleto o con formato inválido.
- `401`: las credenciales no coinciden, el token devuelto por Auth no se puede verificar o la autenticación falla por token inválido/expirado.
- `404`: el usuario solicitado no existe en Auth.
- `409`: el microservicio Auth reporta un conflicto de negocio al intentar autenticar.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio Auth está caído, no responde o devuelve `INTERNAL`.

#### POST /auth/register

Request

- Body JSON: `{ email: string, password: string }`

Response

```json
{
	"message": "string"
}
```

Estados

- `201`: el usuario fue creado correctamente en Auth.
- `400`: el body llega incompleto o inválido.
- `409`: el email o el usuario ya existen.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio Auth está caído, no responde o devuelve `INTERNAL`.

### Player classes

#### GET /player-class/:id

Request

- Path param `id`

Response

```json
{
	"message": "string",
	"data": {
		"id": 1,
		"name": "string",
		"description": "string",
		"critRate": 0,
		"critDamage": 0,
		"hp": 0,
		"atk": 0,
		"def": 0,
		"stamina": 0,
		"accuracy": 0,
		"evasion": 0
	}
}
```

Estados

- `200`: la clase existe y se devuelve su detalle.
- `400`: el `id` no es válido.
- `404`: no existe una clase con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de clases está caído, no responde o devuelve `INTERNAL`.

#### GET /player-class/scaled-stats/:id

Request

- Path param `id`
- Query params opcionales: `hp`, `atk`, `def`, `stamina`, `accuracy`, `evasion`, `level`

Response

```json
{
	"message": "string",
	"scalatedStats": {
		"hp": 0,
		"atk": 0,
		"def": 0,
		"stamina": 0,
		"accuracy": 0,
		"evasion": 0,
		"critRate": 0,
		"critDamage": 0
	}
}
```

Nota: el gateway expone `scalatedStats` con esa ortografía heredada.

Estados

- `200`: la clase existe y se calculan correctamente sus estadísticas escaladas.
- `400`: el `id` o alguno de los query params no es válido.
- `404`: no existe una clase con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de clases está caído, no responde o devuelve `INTERNAL`.

#### GET /player-class

Request

- Sin body ni query params

Response

```json
{
	"message": "string",
	"classes": [
		{
			"id": 1,
			"name": "string",
			"description": "string",
			"critRate": 0,
			"critDamage": 0,
			"hp": 0,
			"atk": 0,
			"def": 0,
			"stamina": 0,
			"accuracy": 0,
			"evasion": 0
		}
	]
}
```

Estados

- `200`: la lista de clases se obtuvo correctamente.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de clases está caído, no responde o devuelve `INTERNAL`.

#### GET /player-class-modifiers/:id

Request

- Path param `id`

Response

```json
{
	"message": "string",
	"modifiers": {
		"id": "string",
		"critRateModifier": 0,
		"critDamageModifier": 0,
		"hpModifier": 0,
		"atkModifier": 0,
		"defModifier": 0,
		"staminaModifier": 0,
		"accuracyModifier": 0,
		"evasionModifier": 0
	}
}
```

Estados

- `200`: los modificadores de la clase existen y se devuelven correctamente.
- `400`: el `id` no es válido.
- `404`: no existe un conjunto de modificadores para esa clase.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de modificadores está caído, no responde o devuelve `INTERNAL`.

### Player

#### GET /player/:id

Request

- Path param `id`

Response

```json
{
	"player": {
		"id": 1,
		"userId": 2,
		"classId": 3,
		"name": "string",
		"alive": true,
		"gold": 0,
		"level": 0,
		"experience": 0,
		"experienceLimit": 0,
		"freeStatPoints": 0,
		"hpBonus": 0,
		"atkBonus": 0,
		"defBonus": 0,
		"staminaBonus": 0,
		"accuracyBonus": 0,
		"evasionBonus": 0
	}
}
```

Estados

- `200`: el jugador existe y se devuelve su detalle.
- `400`: el `id` no es válido.
- `404`: no existe un jugador con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de jugador está caído, no responde o devuelve `INTERNAL`.

#### GET /player/user/:userId

Request

- Path param `userId`

Response

```json
{
	"player": {
		"id": 1,
		"userId": 2,
		"classId": 3,
		"name": "string",
		"alive": true,
		"gold": 0,
		"level": 0,
		"experience": 0,
		"experienceLimit": 0,
		"freeStatPoints": 0,
		"hpBonus": 0,
		"atkBonus": 0,
		"defBonus": 0,
		"staminaBonus": 0,
		"accuracyBonus": 0,
		"evasionBonus": 0
	}
}
```

Estados

- `200`: existe un jugador asociado al usuario y se devuelve su detalle.
- `400`: el `userId` no es válido.
- `404`: no existe un jugador para ese usuario.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de jugador está caído, no responde o devuelve `INTERNAL`.

#### GET /player/top/:limit/:alive

Request

- Path params `limit` y `alive` (`true` o `false`)

Response

```json
{
	"players": [
		{
			"name": "string",
			"level": 0
		}
	]
}
```

Estados

- `200`: se devuelve el ranking de jugadores solicitado.
- `400`: `limit` o `alive` no son válidos.
- `404`: no hay jugadores que coincidan con el filtro solicitado.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de jugador está caído, no responde o devuelve `INTERNAL`.

#### POST /player/update

Request

- Body JSON: `{ playerId, updates }`
- `updates` puede incluir `name`, `gold`, `level`, `experience`, `experienceLimit`, `freeStatPoints`, `hpBonus`, `atkBonus`, `defBonus`, `staminaBonus`, `accuracyBonus`, `evasionBonus`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el jugador se actualizó correctamente.
- `400`: el body está incompleto, vacío o con valores inválidos.
- `404`: no existe un jugador con el `playerId` pedido.
- `409`: el update choca con una restricción de negocio.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de jugador está caído, no responde o devuelve `INTERNAL`.

#### POST /player/delete

Request

- Body JSON: `{ playerId }`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el jugador se eliminó correctamente.
- `400`: el body está incompleto o `playerId` es inválido.
- `404`: no existe un jugador con ese `playerId`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de jugador está caído, no responde o devuelve `INTERNAL`.

### Inventory e items

#### GET /inventory/player/:playerId

Request

- Path param `playerId`

Response

```json
{
	"message": "string",
	"items": [
		{
			"inventoryItemId": 1,
			"name": "string",
			"description": "string",
			"gold": 0,
			"requiredLevel": 0,
			"type": "string",
			"slot": "string",
			"hpBonus": 0,
			"atkBonus": 0,
			"defBonus": 0,
			"staminaBonus": 0,
			"accuracyBonus": 0,
			"evasionBonus": 0,
			"critRateBonus": 0,
			"critDamageBonus": 0,
			"equipped": false
		}
	]
}
```

Estados

- `200`: el inventario existe y se devuelve el listado de items.
- `400`: el `playerId` no es válido.
- `404`: no existe inventario para ese jugador.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### GET /inventory/player/equipped-items/:playerId

Request

- Path param `playerId`

Response

```json
{
	"message": "string",
	"items": [
		{
			"inventoryItemId": 1,
			"name": "string",
			"description": "string",
			"gold": 0,
			"requiredLevel": 0,
			"type": "string",
			"slot": "string",
			"hpBonus": 0,
			"atkBonus": 0,
			"defBonus": 0,
			"staminaBonus": 0,
			"accuracyBonus": 0,
			"evasionBonus": 0,
			"critRateBonus": 0,
			"critDamageBonus": 0,
			"equipped": false
		}
	]
}
```

Estados

- `200`: el jugador tiene items equipados o la lista viene vacía si no tiene ninguno.
- `400`: el `playerId` no es válido.
- `404`: no existe un jugador o no se encontraron items equipados para ese jugador.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### POST /inventory/player/add-item

Request

- Body JSON: `{ playerId, itemId }`

Response

```json
{
	"message": "string",
	"inventoryItemId": 1
}
```

Estados

- `200`: el item fue agregado al inventario.
- `400`: el body está incompleto o los IDs son inválidos.
- `404`: no existe el jugador o el item solicitado.
- `409`: el item ya existe en el inventario o el estado del jugador impide agregarlo.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### POST /inventory/player/remove-item

Request

- Body JSON: `{ inventoryItemId }`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el item fue removido correctamente.
- `400`: el body está incompleto o `inventoryItemId` es inválido.
- `404`: no existe un item de inventario con ese ID.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### POST /inventory/player/equip-item

Request

- Body JSON: `{ inventoryItemId, playerLevel }`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el item fue equipado correctamente.
- `400`: el body está incompleto o el nivel/ID es inválido.
- `404`: no existe el item de inventario.
- `409`: el item no puede equiparse por una restricción de negocio, por ejemplo nivel insuficiente o slot ocupado.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### POST /inventory/player/unequip-item

Request

- Body JSON: `{ inventoryItemId }`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el item fue desequipado correctamente.
- `400`: el body está incompleto o `inventoryItemId` es inválido.
- `404`: no existe el item de inventario o no está equipado.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### GET /inventory/player/equipment-stat-bonus/:playerId

Request

- Path param `playerId`

Response

```json
{
	"hp": 0,
	"atk": 0,
	"def": 0,
	"stamina": 0,
	"accuracy": 0,
	"evasion": 0,
	"critRate": 0,
	"critDamage": 0
}
```

Estados

- `200`: el bono del equipo se calculó correctamente.
- `400`: el `playerId` no es válido.
- `404`: no existe un jugador o no tiene equipo asociado.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de inventario está caído, no responde o devuelve `INTERNAL`.

#### GET /item/by-required-level/:requiredLevel/:onlyHigherThan

Request

- Path params `requiredLevel` y `onlyHigherThan` (`true` o `false`)

Response

```json
{
	"message": "string",
	"items": []
}
```

Estados

- `200`: se devolvió el listado de items filtrado por nivel requerido.
- `400`: `requiredLevel` u `onlyHigherThan` tienen un formato inválido.
- `404`: no se encontraron items para ese filtro.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de items está caído, no responde o devuelve `INTERNAL`.

#### GET /item/by-required-level/:requiredLevel/:onlyHigherThan/:slot

Request

- Path params `requiredLevel`, `onlyHigherThan` y `slot`

Response

```json
{
	"message": "string",
	"items": []
}
```

Estados

- `200`: se devolvió el listado de items filtrado por nivel y slot.
- `400`: alguno de los path params no es válido.
- `404`: no se encontraron items para ese filtro.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de items está caído, no responde o devuelve `INTERNAL`.

`items` usa esta forma común:

```json
{
	"inventoryItemId": 1,
	"name": "Item name",
	"description": "...",
	"gold": 100,
	"requiredLevel": 5,
	"type": "...",
	"slot": "...",
	"hpBonus": 0,
	"atkBonus": 0,
	"defBonus": 0,
	"staminaBonus": 0,
	"accuracyBonus": 0,
	"evasionBonus": 0,
	"critRateBonus": 0,
	"critDamageBonus": 0,
	"equipped": false
}
```

### Combat

#### POST /combat/process

Request

- Body JSON: `{ playerId: number, enemyId: number }`

Response

```json
{
	"combatId": 1,
	"wasFatal": true,
	"totalTurns": 3,
	"turns": [
		{
			"turnNumber": 1,
			"playerAction": {
				"turnAction": "string",
				"turnResult": "string",
				"damage": 0,
				"critical": false
			},
			"enemyAction": {
				"turnAction": "string",
				"turnResult": "string",
				"damage": 0,
				"critical": false
			},
			"playerStateAfter": {
				"hp": 0,
				"stamina": 0,
				"accuracy": 0,
				"evasion": 0
			},
			"enemyStateAfter": {
				"hp": 0,
				"stamina": 0,
				"accuracy": 0,
				"evasion": 0
			}
		}
	],
	"loot": {
		"gold": 0,
		"experience": 0,
		"itemId": 1
	}
}
```

Estados

- `200`: el combate se procesó correctamente y se devuelve el replay completo.
- `400`: el body del combate es inválido.
- `404`: no existe el jugador o el enemigo solicitado.
- `409`: el combate no se puede procesar por el estado actual del recurso.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de combate está caído, no responde o devuelve `INTERNAL`.

#### GET /combat-history/by-player/:id

Request

- Path param `id`

Response

```json
{
	"combats": [
		{
			"enemyId": 1,
			"wasFatal": false,
			"date": "string",
			"totalTurns": 0
		}
	]
}
```

Estados

- `200`: se devolvió el historial del jugador.
- `400`: el `id` no es válido.
- `404`: no existe el jugador o no tiene historial.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de historial de combate está caído, no responde o devuelve `INTERNAL`.

#### GET /combat-history/recent/:limit

Request

- Path param `limit`

Response

```json
{
	"combats": [
		{
			"enemyId": 1,
			"wasFatal": false,
			"date": "string",
			"totalTurns": 0
		}
	]
}
```

Estados

- `200`: se devolvió la cantidad pedida de combates recientes.
- `400`: `limit` no es válido.
- `404`: no hay combates recientes para devolver.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de historial de combate está caído, no responde o devuelve `INTERNAL`.

#### GET /combat-history/win-count/:playerId

Request

- Path param `playerId`

Response

```json
{
	"winCount": 0
}
```

Estados

- `200`: se devolvió el conteo de victorias.
- `400`: el `playerId` no es válido.
- `404`: no existe el jugador o no tiene victorias registradas.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de historial de combate está caído, no responde o devuelve `INTERNAL`.

#### GET /fatal-combat-replay/:id

Request

- Path param `id`

Response

```json
{
	"turns": [
		{
			"turnNumber": 1,
			"playerAction": {
				"turnAction": "string",
				"turnResult": "string",
				"damage": 0,
				"critical": false
			},
			"enemyAction": {
				"turnAction": "string",
				"turnResult": "string",
				"damage": 0,
				"critical": false
			},
			"playerStateAfter": {
				"hp": 0,
				"stamina": 0,
				"accuracy": 0,
				"evasion": 0
			},
			"enemyStateAfter": {
				"hp": 0,
				"stamina": 0,
				"accuracy": 0,
				"evasion": 0
			}
		}
	]
}
```

Estados

- `200`: se devolvió el replay fatal completo.
- `400`: el `id` no es válido.
- `404`: no existe un replay fatal para ese combate.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de replay fatal está caído, no responde o devuelve `INTERNAL`.

#### GET /fatal-combat-replay/recent-fatalities/:limit

Request

- Path param `limit`

Response

```json
{
	"fatalities": [
		{
			"playerId": 1,
			"enemyId": 2,
			"date": "string"
		}
	]
}
```

Estados

- `200`: se devolvió el listado de muertes recientes.
- `400`: `limit` no es válido.
- `404`: no hay muertes recientes para devolver.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de replay fatal está caído, no responde o devuelve `INTERNAL`.

`combat/process` devuelve un replay completo del combate, con esta estructura resumida:

- `turns[]`: número de turno, acción del jugador, acción del enemigo y estado posterior de ambos.
- `loot`: oro, experiencia e `itemId` opcional.

### Tower

#### GET /tower/:id

Request

- Path param `id`

Response

```json
{
	"tower": {
		"id": 1,
		"floor": 1,
		"levelRange": "1-10"
	}
}
```

Estados

- `200`: el piso de la torre existe y se devuelve correctamente.
- `400`: el `id` no es válido.
- `404`: no existe un piso con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de torre está caído, no responde o devuelve `INTERNAL`.

#### GET /tower

Request

- Sin body ni query params

Response

```json
{
	"floors": [
		{
			"id": 1,
			"floor": 1,
			"levelRange": "1-10"
		}
	]
}
```

Estados

- `200`: se devolvió el listado de pisos de la torre.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de torre está caído, no responde o devuelve `INTERNAL`.

#### GET /tower-enemy/:id

Request

- Path param `id`

Response

```json
{
	"enemies": [
		{
			"enemyId": 1
		}
	]
}
```

Estados

- `200`: se devolvió el listado de enemigos del piso.
- `400`: el `id` no es válido.
- `404`: no existe un piso con ese `id` o no tiene enemigos asociados.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de enemigos de torre está caído, no responde o devuelve `INTERNAL`.

#### GET /tower-player-progress/:playerId

Request

- Path param `playerId`

Response

```json
{
	"progress": {
		"floor": 1,
		"levelRange": "1-10"
	}
}
```

Estados

- `200`: el progreso de torre del jugador existe y se devuelve correctamente.
- `400`: el `playerId` no es válido.
- `404`: no existe progreso para ese jugador.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de progreso de torre está caído, no responde o devuelve `INTERNAL`.

#### POST /tower-player-progress/register

Request

- Body JSON: `{ towerId, playerId }`

Response

```json
{
	"message": "string"
}
```

Estados

- `200`: el progreso fue registrado correctamente.
- `400`: el body está incompleto o inválido.
- `404`: no existe la torre o el jugador solicitado.
- `409`: ya existe progreso para ese jugador y torre.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de progreso de torre está caído, no responde o devuelve `INTERNAL`.

### World

#### GET /map/:id

Request

- Path param `id`

Response

```json
{
	"map": {
		"id": 1,
		"name": "string",
		"description": "string",
		"rangeLevel": "1-10"
	}
}
```

Estados

- `200`: el mapa existe y se devuelve correctamente.
- `400`: el `id` no es válido.
- `404`: no existe un mapa con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de mapas está caído, no responde o devuelve `INTERNAL`.

#### GET /map

Request

- Sin body ni query params

Response

```json
{
	"maps": [
		{
			"map": {
				"id": 1,
				"name": "string",
				"description": "string",
				"rangeLevel": "1-10"
			}
		}
	]
}
```

Estados

- `200`: se devolvió el listado de mapas.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de mapas está caído, no responde o devuelve `INTERNAL`.

#### GET /map/enemies/:id

Request

- Path param `id`

Response

```json
{
	"pool": [
		{
			"id": 1,
			"name": "string",
			"description": "string",
			"gold": 0
		}
	]
}
```

Estados

- `200`: se devolvió el pool de enemigos del mapa.
- `400`: el `id` no es válido.
- `404`: no existe un mapa con ese `id` o no tiene enemigos.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de mapas está caído, no responde o devuelve `INTERNAL`.

#### GET /map/enemies-expanded/:id

Request

- Path param `id`

Response

```json
{
	"enemies": [
		{
			"id": 1,
			"name": "string",
			"description": "string",
			"type": "string",
			"level": 0,
			"experience": 0,
			"gold": 0,
			"critRate": 0,
			"critDamage": 0,
			"hp": 0,
			"atk": 0,
			"def": 0,
			"stamina": 0,
			"accuracy": 0,
			"evasion": 0
		}
	]
}
```

Estados

- `200`: se devolvió el pool expandido de enemigos.
- `400`: el `id` no es válido.
- `404`: no existe un mapa con ese `id` o no tiene enemigos.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de mapas está caído, no responde o devuelve `INTERNAL`.

#### GET /enemy/:id

Request

- Path param `id`

Response

```json
{
	"enemy": {
		"id": 1,
		"name": "string",
		"description": "string",
		"type": "string",
		"level": 0,
		"experience": 0,
		"gold": 0,
		"critRate": 0,
		"critDamage": 0,
		"hp": 0,
		"atk": 0,
		"def": 0,
		"stamina": 0,
		"accuracy": 0,
		"evasion": 0
	}
}
```

Estados

- `200`: el enemigo existe y se devuelve correctamente.
- `400`: el `id` no es válido.
- `404`: no existe un enemigo con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de enemigos está caído, no responde o devuelve `INTERNAL`.

#### GET /npc/:id

Request

- Path param `id`

Response

```json
{
	"npc": {
		"id": 1,
		"name": "string",
		"description": "string",
		"type": "string"
	}
}
```

Estados

- `200`: el NPC existe y se devuelve correctamente.
- `400`: el `id` no es válido.
- `404`: no existe un NPC con ese `id`.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de NPCs está caído, no responde o devuelve `INTERNAL`.

#### GET /npc

Request

- Sin body ni query params

Response

```json
{
	"npcs": [
		{
			"id": 1,
			"name": "string",
			"description": "string",
			"type": "string"
		}
	]
}
```

Estados

- `200`: se devolvió el listado de NPCs.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de NPCs está caído, no responde o devuelve `INTERNAL`.

#### GET /npc-item/:npcId

Request

- Path param `npcId`

Response

```json
{
	"items": [
		{
			"id": 1,
			"itemId": 2,
			"price": 100
		}
	]
}
```

Estados

- `200`: se devolvió el inventario de tienda del NPC.
- `400`: el `npcId` no es válido.
- `404`: no existe un NPC con ese `npcId` o no vende items.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de NPC items está caído, no responde o devuelve `INTERNAL`.

### Loot

#### GET /loot/drop-table/:enemyId

Request

- Path param `enemyId`

Response

```json
{
	"dropTable": [
		{
			"itemId": 1,
			"dropChance": 0.25
		}
	]
}
```

Estados

- `200`: se devolvió la tabla de drops del enemigo.
- `400`: el `enemyId` no es válido.
- `404`: no existe un enemigo con ese `enemyId` o no tiene tabla de drops.
- `500`: error no tipado o no mapeado en el gateway.
- `503`: el microservicio de loot está caído, no responde o devuelve `INTERNAL`.

## Dockerización

El proyecto usa un `Dockerfile` multi-stage:

1. Imagen de build con Node 20-alpine.
2. Instalación de dependencias y compilación con `npm run build`.
3. Imagen final más liviana, con dependencias de producción y los `proto` copiados al filesystem final.

Build de la imagen:

```bash
docker build -t web-mmorpg-gateway .
```

Ejecución del contenedor:

```bash
docker run --rm -p 3000:3000 --env-file .env web-mmorpg-gateway
```

Si usas otro puerto o secret, ajusta `PORT` y `JWT_SECRET` en el entorno del contenedor.

## Observaciones útiles

- Este gateway no contiene la lógica de negocio principal; casi todo pasa por servicios gRPC.
- Muchas respuestas están normalizadas por mappers internos para convertir `Long` de gRPC a `number` en HTTP.
- Varias rutas devuelven objetos vacíos seguros cuando el microservicio no responde con listas, por ejemplo `items: []`, `maps: []` o `enemies: []`.
- El login deja una cookie HTTP-only, lo que reduce exposición del JWT en el frontend.
