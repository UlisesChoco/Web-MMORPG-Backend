export interface GetMapResponse {
  map: Map;
}

export interface Map {
  id: Long;
  name: string;
  description: string;
  rangeLevel: string;
}
