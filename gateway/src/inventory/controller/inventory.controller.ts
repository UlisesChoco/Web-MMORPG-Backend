import {
  Body,
  Controller,
  ForbiddenException,
  Get,
  HttpCode,
  HttpStatus,
  Inject,
  Logger,
  OnModuleInit,
  Param,
  ParseIntPipe,
  Post,
  Req,
  Res,
} from '@nestjs/common';
import { InventoryService } from '../service/inventory.service';
import { PlayerService } from 'src/player/service/player.service';
import type { ClientGrpc } from '@nestjs/microservices/interfaces/client-grpc.interface';
import { firstValueFrom } from 'rxjs/internal/firstValueFrom';
import { Http } from 'src/common/http/http.handle.response';
import { OwnershipGuard } from 'src/common/auth/ownership.guard';
import type { Response } from 'express';
import { InventoryMapper } from '../mapper/inventory.mapper';
import { AddItemToInventoryRequestDTO } from '../dto/inventory/add-item-to-inventory-request.dto';
import { RemoveItemFromInventoryRequestDTO } from '../dto/inventory/remove-item-from-inventory-request.dto';
import { EquipItemRequestDTO } from '../dto/inventory/equip-item-request.dto';
import { UnequipItemRequestDTO } from '../dto/inventory/unequip-item-request.dto';

@Controller('inventory')
export class InventoryController implements OnModuleInit {
  private inventoryService: InventoryService;
  private playerService: PlayerService;
  private readonly logger = new Logger(InventoryController.name);

  constructor(
    @Inject('INVENTORY_PACKAGE') private readonly inventoryClient: ClientGrpc,
    @Inject('PLAYER_PACKAGE') private readonly playerClient: ClientGrpc,
  ) {}

  onModuleInit() {
    this.inventoryService =
      this.inventoryClient.getService<InventoryService>('InventoryService');
    this.playerService =
      this.playerClient.getService<PlayerService>('PlayerService');
  }

  @Get('player/:playerId')
  @HttpCode(HttpStatus.OK)
  async getPlayerInventory(
    @Param('playerId', ParseIntPipe) playerId: number,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        playerId,
        this.logger,
        'getPlayerInventory',
      );

      const observable = this.inventoryService.GetPlayerInventory({
        playerId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = InventoryMapper.toGetPlayerInventoryResponseDTO(data);

      this.logger.log(`Se obtuvo el inventario del jugador ${playerId}`);

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el inventario del jugador ${playerId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('player/equipped-items/:playerId')
  @HttpCode(HttpStatus.OK)
  async getEquippedItems(
    @Param('playerId', ParseIntPipe) playerId: number,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        playerId,
        this.logger,
        'getEquippedItems',
      );

      const observable = this.inventoryService.GetEquippedItems({
        playerId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = InventoryMapper.toGetEquippedItemsResponseDTO(data);

      this.logger.log(
        `Se obtuvo el inventario equipado del jugador ${playerId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el inventario equipado del jugador ${playerId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('player/add-item')
  @HttpCode(HttpStatus.OK)
  async addItemToInventory(
    @Body() dto: AddItemToInventoryRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'addItemToInventory',
      );

      const observable = this.inventoryService.AddItemToInventory({
        playerId: dto.playerId,
        itemId: dto.itemId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = InventoryMapper.toAddItemToInventoryResponseDTO(data);

      this.logger.log(
        `Se agregó el ítem de ID ${dto.itemId} al inventario del jugador ${dto.playerId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al agregar el ítem de ID ${dto.itemId} al inventario del jugador ${dto.playerId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('player/remove-item')
  @HttpCode(HttpStatus.OK)
  async removeItemFromInventory(
    @Body() dto: RemoveItemFromInventoryRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'removeItemFromInventory',
      );

      const observable = this.inventoryService.RemoveItemFromInventory({
        inventoryItemId: dto.inventoryItemId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData =
        InventoryMapper.toRemoveItemFromInventoryResponseDTO(data);

      this.logger.log(
        `Se eliminó el ítem del inventario de ID ${dto.inventoryItemId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al eliminar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('player/equip-item')
  @HttpCode(HttpStatus.OK)
  async equipItem(
    @Body() dto: EquipItemRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'equipItem',
      );

      const observable = this.inventoryService.EquipItem({
        inventoryItemId: dto.inventoryItemId,
        playerLevel: dto.playerLevel,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = InventoryMapper.toEquipItemResponseDTO(data);

      this.logger.log(
        `Se equipó el ítem del inventario de ID ${dto.inventoryItemId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al equipar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Post('player/unequip-item')
  @HttpCode(HttpStatus.OK)
  async unequipItem(
    @Body() dto: UnequipItemRequestDTO,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        dto.playerId,
        this.logger,
        'unequipItem',
      );

      const observable = this.inventoryService.UnequipItem({
        inventoryItemId: dto.inventoryItemId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData = InventoryMapper.toUnequipItemResponseDTO(data);

      this.logger.log(
        `Se desequipó el ítem del inventario de ID ${dto.inventoryItemId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al desequipar el ítem del inventario de ID ${dto.inventoryItemId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }

  @Get('player/equipment-stat-bonus/:playerId')
  @HttpCode(HttpStatus.OK)
  async getEquipmentStatBonus(
    @Param('playerId', ParseIntPipe) playerId: number,
    @Req() req: any,
    @Res({ passthrough: true }) response: Response,
  ) {
    try {
      await OwnershipGuard.verifyPlayerOwnership(
        this.playerService,
        req.user.userId,
        playerId,
        this.logger,
        'getEquipmentStatBonus',
      );

      const observable = this.inventoryService.GetEquipmentStatBonus({
        playerId,
      } as any);
      const data = await firstValueFrom(observable);
      const finalData =
        InventoryMapper.toGetEquipmentStatBonusResponseDTO(data);

      this.logger.log(
        `Se obtuvo el bono de estadísticas del equipo del jugador ${playerId}`,
      );

      return finalData;
    } catch (err) {
      if (err instanceof ForbiddenException) throw err;
      this.logger.error(
        `Error al obtener el bono de estadísticas del equipo del jugador ${playerId}: ${err}`,
      );
      return Http.handleHttpErrorResponse(err, response);
    }
  }
}
