import Long from 'long';

export class Util {
    static longToNumber(value: Long | null | undefined): number {
        if (value === null || value === undefined)
            return 0;

        return value.toNumber();
    }
}