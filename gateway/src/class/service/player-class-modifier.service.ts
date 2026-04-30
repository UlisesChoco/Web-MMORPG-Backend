import { Observable } from "rxjs";
import { GetClassModifiersRequest } from "../interfaces/modifier/request/get-class-modifiers-request";
import { GetClassModifiersResponse } from "../interfaces/modifier/response/get-class-modifiers-response";

export interface PlayerClassModifierService {
    GetClassModifiers(request: GetClassModifiersRequest): Observable<GetClassModifiersResponse>;
}