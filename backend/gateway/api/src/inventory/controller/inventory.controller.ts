import { Body, Controller, Get, HttpCode, HttpStatus, Inject, Logger, OnModuleInit, Param, Post, Res } from "@nestjs/common";
import { InventoryService } from "../service/inventory.service";
import type { ClientGrpc } from "@nestjs/microservices/interfaces/client-grpc.interface";
import { firstValueFrom } from "rxjs/internal/firstValueFrom";
import { Http } from "src/common/http/http.handle.response";
import type { Response } from "express";
import { InventoryMapper } from "../mapper/inventory.mapper";
import type { AddItemToInventoryRequestDTO } from "../dto/inventory/add-item-to-inventory-request.dto";
import type { RemoveItemFromInventoryRequestDTO } from "../dto/inventory/remove-item-from-inventory-request.dto";
import type { EquipItemRequestDTO } from "../dto/inventory/equip-item-request.dto";
import type { UnequipItemRequestDTO } from "../dto/inventory/unequip-item-request.dto";

@Controller('inventory')
export class InventoryController implements OnModuleInit {
    private inventoryService: InventoryService;
    private readonly logger = new Logger(InventoryController.name);

    constructor(@Inject('INVENTORY_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.inventoryService = this.client.getService<InventoryService>('InventoryService');
    }

    @Get('player/:playerId')
    @HttpCode(HttpStatus.OK)
    async getPlayerInventory(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.playerId,
            }

            const observable = this.inventoryService.GetPlayerInventory(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toGetPlayerInventoryResponseDTO(data);

            this.logger.log(`Se obtuvo el inventario del jugador ${params.playerId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el inventario del jugador ${params.playerId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('player/equipped-items/:playerId')
    @HttpCode(HttpStatus.OK)
    async getEquippedItems(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.playerId,
            }

            const observable = this.inventoryService.GetEquippedItems(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toGetEquippedItemsResponseDTO(data);

            this.logger.log(`Se obtuvo el inventario equipado del jugador ${params.playerId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el inventario equipado del jugador ${params.playerId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('player/add-item')
    @HttpCode(HttpStatus.OK)
    async addItemToInventory(
        @Body() dto: AddItemToInventoryRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: dto.playerId,
                itemId: dto.itemId,
            }

            const observable = this.inventoryService.AddItemToInventory(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toAddItemToInventoryResponseDTO(data);

            this.logger.log(`Se agregó el ítem de ID ${dto.itemId} al inventario del jugador ${dto.playerId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al agregar el ítem de ID ${dto.itemId} al inventario del jugador ${dto.playerId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('player/remove-item')
    @HttpCode(HttpStatus.OK)
    async removeItemFromInventory(
        @Body() dto: RemoveItemFromInventoryRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                inventoryItemId: dto.inventoryItemId,
            }

            const observable = this.inventoryService.RemoveItemFromInventory(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toRemoveItemFromInventoryResponseDTO(data);

            this.logger.log(`Se eliminó el ítem del inventario de ID ${dto.inventoryItemId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al eliminar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('player/equip-item')
    @HttpCode(HttpStatus.OK)
    async equipItem(
        @Body() dto: EquipItemRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                inventoryItemId: dto.inventoryItemId,
                playerLevel: dto.playerLevel,
            }

            const observable = this.inventoryService.EquipItem(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toEquipItemResponseDTO(data);

            this.logger.log(`Se equipó el ítem del inventario de ID ${dto.inventoryItemId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al equipar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('player/unequip-item')
    @HttpCode(HttpStatus.OK)
    async unequipItem(
        @Body() dto: UnequipItemRequestDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                inventoryItemId: dto.inventoryItemId
            }

            const observable = this.inventoryService.UnequipItem(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toUnequipItemResponseDTO(data);

            this.logger.log(`Se desequipó el ítem del inventario de ID ${dto.inventoryItemId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al desequipar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Get('player/equipment-stat-bonus/:playerId')
    @HttpCode(HttpStatus.OK)
    async getEquipmentStatBonus(
        @Param() params: any,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const request = {
                playerId: params.playerId,
            }

            const observable = this.inventoryService.GetEquipmentStatBonus(request);

            const data = await firstValueFrom(observable);

            const finalData = InventoryMapper.toGetEquipmentStatBonusResponseDTO(data);

            this.logger.log(`Se obtuvo el bono de estadísticas del equipo del jugador ${params.playerId}`);

            return finalData;
        } catch (err) {
            this.logger.error(`Error al obtener el bono de estadísticas del equipo del jugador ${params.playerId}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}