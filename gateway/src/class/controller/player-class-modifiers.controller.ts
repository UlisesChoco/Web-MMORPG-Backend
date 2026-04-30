import { Controller, Get, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { PlayerClassModifierService } from "../service/player-class-modifier.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";

@Controller('player-class-modifiers')
export class PlayerClassModifiersController implements OnModuleInit {
    private playerClassModifierService: PlayerClassModifierService;
    private readonly logger = new Logger(PlayerClassModifiersController.name);

    constructor(@Inject('PLAYER_CLASS_MODIFIER_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.playerClassModifierService = this.client.getService<PlayerClassModifierService>('PlayerClassModifierService');
    }

    @Get(':id')
    @HttpCode(HttpStatus.OK)
    async getClassModifiers(
        @Param() params: any,
        @Res({ passthrough: true }) res: Response
    ) {
        try {
            const request = {
                classId: params.id
            };

            const observable = this.playerClassModifierService.GetClassModifiers(request);

            const data = await firstValueFrom(observable);

            this.logger.log(`Modificadores de clase obtenidos para la clase con ID ${params.id}`);

            return data;
        } catch (err) {
            this.logger.error(`Error al obtener los modificadores de clase para la clase con ID ${params.id}: ${err}`);

            return Http.handleHttpErrorResponse(err, res);
        }
    }
}