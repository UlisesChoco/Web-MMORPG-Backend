import { ClassData } from "./get-class-by-id-response";

export interface ListClassesResponse {
    message: string;
    classes: Array<ClassData>
}