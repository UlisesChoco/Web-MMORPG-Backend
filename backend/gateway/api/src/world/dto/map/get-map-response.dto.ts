export class GetMapResponseDTO {
  map: MapDTO;
}

export class MapDTO {
  id: number;
  name: string;
  description: string;
  rangeLevel: string;
}
