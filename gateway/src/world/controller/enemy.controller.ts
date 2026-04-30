import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { EnemyService } from "../service/enemy.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { EnemyMapper } from "../mapper/enemy.mapper";

@Controller('enemy')
export class EnemyController implements OnModuleInit {
    private enemyService: EnemyService;
    private readonly logger = new Logger(EnemyController.name);

    constructor(@Inject('ENEMY_PACKAGE') private readonly client: ClientGrpc) { }

    onModuleInit() {
        this.enemyService = this.client.getService<EnemyService>('EnemyService');
    }

    @Get(':id')
    async getEnemyById(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                enemyId: params.id
            };

            const observable = this.enemyService.GetEnemyById(request);

            const data = await firstValueFrom(observable);

            const finalData = EnemyMapper.toGetEnemyByIdResponseDTO(data);

            this.logger.log(`Enemigo con ID ${params.id} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el enemigo con ID ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}