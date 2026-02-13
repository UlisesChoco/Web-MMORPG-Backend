import { Observable } from "rxjs";
import { GetExpandedMapEnemyPoolRequest } from "../interfaces/map/request/get-expanded-map-enemy-pool-request.interface";
import { GetMapByIdRequest } from "../interfaces/map/request/get-map-by-id-request.interface";
import { ListMapEnemiesRequest } from "../interfaces/map/request/list-map-enemies-request.interface";
import { ListMapsRequest } from "../interfaces/map/request/list-maps-request.interface";
import { GetExpandedMapEnemyPoolResponse } from "../interfaces/map/response/get-expanded-map-enemy-pool-response.interface";
import { GetMapResponse } from "../interfaces/map/response/get-map-response.interface";
import { ListMapEnemiesResponse } from "../interfaces/map/response/list-map-enemies-response.interface";
import { ListMapsResponse } from "../interfaces/map/response/list-maps-response.interface";

export interface MapService {
  GetMapById(request: GetMapByIdRequest): Observable<GetMapResponse>;

  ListMaps(request: ListMapsRequest): Observable<ListMapsResponse>;

  ListMapEnemies(request: ListMapEnemiesRequest): Observable<ListMapEnemiesResponse>;

  GetExpandedMapEnemyPool(request: GetExpandedMapEnemyPoolRequest): Observable<GetExpandedMapEnemyPoolResponse>;
}
