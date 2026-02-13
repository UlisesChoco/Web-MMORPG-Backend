import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { MapService } from "../service/map.service";
import type { ClientGrpc } from "@nestjs/microservices/interfaces/client-grpc.interface";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { MapMapper } from "../mapper/map.mapper";

@Controller('map')
export class MapController implements OnModuleInit {
    private mapService: MapService;
    private readonly logger = new Logger(MapController.name);

    constructor(@Inject('MAP_PACKAGE') private readonly client: ClientGrpc) { }

    onModuleInit() {
        this.mapService = this.client.getService<MapService>('MapService');
    }

    @Get(':id')
    async getMapById(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                mapId: params.id
            };

            const observable = await this.mapService.GetMapById(request);

            const data = await firstValueFrom(observable);

            const finalData = MapMapper.toGetMapResponseDTO(data);

            this.logger.log(`Mapa con id ${params.id} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el mapa con id ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get()
    async listMaps(
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {};

            const observable = await this.mapService.ListMaps(request);

            const data = await firstValueFrom(observable);

            const finalData = MapMapper.toListMapsResponseDTO(data);

            this.logger.log(`Lista de mapas obtenida exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener la lista de mapas: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('enemies/:id')
    async listMapEnemies(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                mapId: params.id
            };

            const observable = await this.mapService.ListMapEnemies(request);

            const data = await firstValueFrom(observable);

            const finalData = MapMapper.toListMapEnemiesResponseDTO(data);

            this.logger.log(`Lista de enemigos del mapa con id ${params.id} obtenida exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener la lista de enemigos del mapa con id ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('enemies-expanded/:id')
    async getExpandedMapEnemyPool(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                mapId: params.id
            };

            const observable = await this.mapService.GetExpandedMapEnemyPool(request);

            const data = await firstValueFrom(observable);

            const finalData = MapMapper.toGetExpandedMapEnemyPoolResponseDTO(data);

            this.logger.log(`Pool de enemigos expandido del mapa con id ${params.id} obtenido exitosamente`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el pool de enemigos expandido del mapa con id ${params.id}: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}