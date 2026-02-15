import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { TowerService } from "../service/tower.service";
import type { ClientGrpc } from "@nestjs/microservices";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { TowerMapper } from "../mapper/tower.mapper";

@Controller('tower')
export class TowerController implements OnModuleInit {
    private towerService: TowerService;
    private readonly logger = new Logger(TowerController.name);

    constructor(@Inject('TOWER_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.towerService = this.client.getService<TowerService>('TowerService');
    }

    @Get(':id')
    async getTowerFloor(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                floor: params.id
            };

            const observable = this.towerService.GetTowerFloor(request);

            const data = await firstValueFrom(observable);

            const finalData = TowerMapper.toGetTowerFloorResponseDTO(data);

            this.logger.log(`Piso de la torre con id ${params.id} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el piso de la torre con id ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get()
    async listTowerFloors(
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {};

            const observable = this.towerService.ListTowerFloors(request);

            const data = await firstValueFrom(observable);

            const finalData = TowerMapper.toListTowerFloorsResponseDTO(data);

            this.logger.log(`Pisos de la torre obtenidos exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener los pisos de la torre: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}