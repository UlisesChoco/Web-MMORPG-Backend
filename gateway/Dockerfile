FROM node:20-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install --omit=dev

RUN mkdir -p src/auth/proto \
    src/class/proto \
    src/combat/proto \
    src/inventory/proto \
    src/loot/proto \
    src/player/proto \
    src/tower/proto \
    src/world/proto

COPY --from=build /app/dist ./dist
COPY --from=build /app/src/auth/proto/*.proto ./src/auth/proto
COPY --from=build /app/src/class/proto/*.proto ./src/class/proto/
COPY --from=build /app/src/combat/proto/*.proto ./src/combat/proto/
COPY --from=build /app/src/inventory/proto/*.proto ./src/inventory/proto/
COPY --from=build /app/src/loot/proto/*.proto ./src/loot/proto/
COPY --from=build /app/src/player/proto/*.proto ./src/player/proto/
COPY --from=build /app/src/tower/proto/*.proto ./src/tower/proto/
COPY --from=build /app/src/world/proto/*.proto ./src/world/proto/

CMD ["node", "dist/main"]
