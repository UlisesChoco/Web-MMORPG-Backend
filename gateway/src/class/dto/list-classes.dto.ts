import { ClassDataDTO } from "./get-class-by-id.dto";

export interface ListClassesDTO {
    message: string;
    classes: Array<ClassDataDTO>;
}