import { Enemy } from "../../enemy/response/get-enemy-by-id-response.interface";

export interface GetExpandedMapEnemyPoolResponse {
  enemies: Array<Enemy>;
}