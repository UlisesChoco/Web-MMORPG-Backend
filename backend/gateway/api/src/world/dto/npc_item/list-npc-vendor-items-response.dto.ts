export class ListNPCVendorItemsResponseDTO {
  items: Array<NPCItemDTO>;
}

export class NPCItemDTO {
  id: number;
  itemId: number;
  price: number;
}
