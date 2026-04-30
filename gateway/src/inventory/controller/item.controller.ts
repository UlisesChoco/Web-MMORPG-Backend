import { Controller, Get, Inject, Logger, OnModuleInit, Param, Res } from "@nestjs/common";
import { ItemService } from "../service/item.service";
import type { ClientGrpc } from "@nestjs/microservices/interfaces/client-grpc.interface";
import type { Response } from "express";
import { Http } from "src/common/http/http.handle.response";
import { firstValueFrom } from "rxjs";
import { ItemMapper } from "../mapper/item.mapper";

@Controller('item')
export class ItemController implements OnModuleInit {
    private itemService: ItemService;
    private readonly logger = new Logger(ItemController.name);

    constructor(@Inject('ITEM_PACKAGE') private readonly client: ClientGrpc) {}
    
    onModuleInit() {
        this.itemService = this.client.getService<ItemService>('ItemService');
    }

    @Get('by-required-level/:requiredLevel/:onlyHigherThan')
    async getItemsByRequiredLevel(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                requiredLevel: params.requiredLevel,
                onlyHigherThan: params.onlyHigherThan,
            }

            const observable = this.itemService.GetItemsByRequiredLevel(request);

            const data = await firstValueFrom(observable);

            const finalData = ItemMapper.toGetItemsByRequiredLevelResponseDTO(data);

            this.logger.log(`Items obtenidos exitosamente por nivel requerido`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener items por nivel requerido: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('by-required-level/:requiredLevel/:onlyHigherThan/:slot')
    async getItemsByRequiredLevelAndSlot(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                requiredLevel: params.requiredLevel,
                onlyHigherThan: params.onlyHigherThan,
                slot: params.slot,
            }

            const observable = this.itemService.GetItemsByRequiredLevelAndSlot(request);

            const data = await firstValueFrom(observable);

            const finalData = ItemMapper.toGetItemsByRequiredLevelAndSlotResponseDTO(data);

            this.logger.log(`Items obtenidos exitosamente por nivel requerido y slot`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener items por nivel requerido y slot: ${err.message}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}