export interface ListNPCVendorItemsResponse {
  items: Array<NPCItem>;
}

export interface NPCItem {
  id: Long;
  itemId: Long;
  price: number;
}
