import { Body, Controller, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Post, Res } from "@nestjs/common";
import { CombatService } from "../service/combat.service";
import type { ClientGrpc } from "@nestjs/microservices";
import { ProcessCombatDTO } from "../dto/combat/process-combat.dto";
import { ProcessCombatRequest } from "../interfaces/combat/request/process-combat-request.interface";
import { firstValueFrom } from "rxjs";
import { Http } from "src/common/http/http.handle.response";
import type { Response } from "express";
import { ProcessCombatResponse } from "../interfaces/combat/response/process-combat-response.interface";
import { CombatMapper } from "../mapper/combat.mapper";

@Controller('combat')
export class CombatController implements OnModuleInit {
    private combatService: CombatService;
    private readonly logger = new Logger(CombatController.name);

    constructor(@Inject('COMBAT_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.combatService = this.client.getService<CombatService>('CombatService');
    }

    @Post('process')
    @HttpCode(HttpStatus.OK)
    async processCombat(
        @Body() dto: ProcessCombatDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request: ProcessCombatRequest = {
                playerId: dto.playerId,
                enemyId: dto.enemyId,
            };

            const observable = this.combatService.ProcessCombat(request);

            const data = await firstValueFrom(observable);

            const finalData = CombatMapper.toProcessCombatResponseDTO(data);

            this.logger.log(`Se procesó un combate entre el jugador ${dto.playerId} y el enemigo ${dto.enemyId}`);

            return finalData;
        } catch(err) {
            this.logger.error(`Error al procesar combate entre el jugador ${dto.playerId} y el enemigo ${dto.enemyId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}