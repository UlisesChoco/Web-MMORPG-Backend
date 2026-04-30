import { Util } from "src/common/util/number.functions";
import { GetClassByIdResponse } from "../interfaces/class/response/get-class-by-id-response";
import { GetClassByIdDTO } from "../dto/get-class-by-id.dto";
import { ListClassesDTO } from "../dto/list-classes.dto";
import { ListClassesResponse } from "../interfaces/class/response/list-classes-response";

export class PlayerClassMapper {
    static toGetClassByIdDTO(data: any): GetClassByIdDTO {
        const response = data as GetClassByIdResponse;

        const dataMapped = {
            id: Util.longToNumber(response.data.id),
            name: response.data.name,
            description: response.data.description,
            critRate: response.data.critRate,
            critDamage: response.data.critDamage,
            hp: response.data.hp,
            atk: response.data.atk,
            def: response.data.def,
            stamina: response.data.stamina,
            accuracy: response.data.accuracy,
            evasion: response.data.evasion,
        };

        const dto = {
            message: response.message,
            data: dataMapped
        };
        
        return dto;
    }

    static toListClassesDTO(data: any): ListClassesDTO {
        const response = data as ListClassesResponse;

        const classesMapped = response.classes.map(cls => {
            return {
                id: Util.longToNumber(cls.id),
                name: cls.name,
                description: cls.description,
                critRate: cls.critRate,
                critDamage: cls.critDamage,
                hp: cls.hp,
                atk: cls.atk,
                def: cls.def,
                stamina: cls.stamina,
                accuracy: cls.accuracy,
                evasion: cls.evasion,
            };
        });
        
        const dto = {
            message: response.message,
            classes: classesMapped
        };

        return dto;
    }
}