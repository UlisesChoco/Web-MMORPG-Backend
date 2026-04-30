import { EnemyDTO } from "../enemy/get-enemy-by-id-response.dto";

export interface GetExpandedMapEnemyPoolResponseDTO {
  enemies: Array<EnemyDTO>;
}