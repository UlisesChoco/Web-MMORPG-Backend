import { Observable } from "rxjs";
import { GetTowerFloorRequest } from "../interfaces/tower/request/get-tower-floor-request.interface";
import { GetTowerFloorResponse } from "../interfaces/tower/response/get-tower-floor-response.interface";
import { ListTowerFloorsRequest } from "../interfaces/tower/request/list-tower-floors-request";
import { ListTowerFloorsResponse } from "../interfaces/tower/response/list-tower-floors-response";

export interface TowerService {
  GetTowerFloor(request: GetTowerFloorRequest): Observable<GetTowerFloorResponse>;
  ListTowerFloors(request: ListTowerFloorsRequest): Observable<ListTowerFloorsResponse>;
}