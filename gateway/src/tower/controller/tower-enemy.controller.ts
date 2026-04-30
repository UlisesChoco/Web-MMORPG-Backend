import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import type { ClientGrpc } from "@nestjs/microservices";
import { TowerEnemyService } from "../service/tower-enemy.service";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { TowerEnemyMapper } from "../mapper/tower-enemy.mapper";

@Controller('tower-enemy')
export class TowerEnemyController implements OnModuleInit {
    private towerEnemyService: TowerEnemyService;
    private readonly logger = new Logger(TowerEnemyController.name);

    constructor(@Inject('TOWER_ENEMY_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.towerEnemyService = this.client.getService<TowerEnemyService>('TowerEnemyService');
    }

    @Get(':id')
    async listTowerEnemies(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                towerId: params.id
            };

            const observable = this.towerEnemyService.ListTowerEnemies(request);

            const data = await firstValueFrom(observable);

            const finalData = TowerEnemyMapper.toListTowerEnemiesResponseDTO(data);

            this.logger.log(`Enemigos de torre listados exitosamente para torreId: ${params.id}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al listar enemigos de torre: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        } 
    }
}