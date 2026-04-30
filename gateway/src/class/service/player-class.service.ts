import { Observable } from "rxjs";
import { GetClassByIdRequest } from "../interfaces/class/request/get-class-by-id-request";
import { GetClassByIdResponse } from "../interfaces/class/response/get-class-by-id-response";
import { GetScaledClassStatsRequest } from "../interfaces/class/request/get-scalated-class-stats-request";
import { GetScaledClassStatsResponse } from "../interfaces/class/response/get-scalated-class-stats-response";
import { ListClassesRequest } from "../interfaces/class/request/list-classes-request";
import { ListClassesResponse } from "../interfaces/class/response/list-classes-response";

export interface PlayerClassService {
    GetClassById(request: GetClassByIdRequest): Observable<GetClassByIdResponse>;
    GetScaledClassStats(request: GetScaledClassStatsRequest): Observable<GetScaledClassStatsResponse>;
    ListClasses(request: ListClassesRequest): Observable<ListClassesResponse>;
}