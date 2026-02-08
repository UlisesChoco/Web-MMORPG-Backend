import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { LootService } from "../service/loot.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";

@Controller('loot')
export class LootController implements OnModuleInit{
    private lootService: LootService;
    private readonly logger = new Logger(LootController.name);

    constructor(@Inject('LOOT_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.lootService = this.client.getService<LootService>('LootService');
    }

    @Get('drop-table/:enemyId')
    async getEnemyDropTable(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                enemyId: params.enemyId
            };

            const observable = this.lootService.GetEnemyDropTable(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Tabla de drops del enemigo con ID ${params.enemyId} obtenida exitosamente.`);

            return data;
        } catch (err) {
            this.logger.error(`Error al obtener la tabla de drops del enemigo con ID ${params.enemyId}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response)
        }
    }
}