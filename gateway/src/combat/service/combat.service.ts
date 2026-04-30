import { Observable } from "rxjs";
import { ProcessCombatRequest } from "../interfaces/combat/request/process-combat-request.interface";
import { ProcessCombatResponse } from "../interfaces/combat/response/process-combat-response.interface";

export interface CombatService {
    ProcessCombat(request: ProcessCombatRequest): Observable<ProcessCombatResponse>;
}