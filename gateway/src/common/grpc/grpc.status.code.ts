import { HttpStatus } from "@nestjs/common";

enum GrpcStatus {
    INVALID_ARGUMENT = 3,
    NOT_FOUND = 5,
    ALREADY_EXISTS = 6,
    PERMISSION_DENIED = 7,
    INTERNAL = 13,
    UNAUTHENTICATED = 16
}

export class GrpcStatusCode {
    static grpcToHttp(grpcCode: number): number {
        switch (grpcCode) {
            case GrpcStatus.INVALID_ARGUMENT: return HttpStatus.BAD_REQUEST;
            case GrpcStatus.NOT_FOUND: return HttpStatus.NOT_FOUND;
            case GrpcStatus.ALREADY_EXISTS: return HttpStatus.CONFLICT;
            case GrpcStatus.INTERNAL: return HttpStatus.SERVICE_UNAVAILABLE;
            case GrpcStatus.UNAUTHENTICATED: return HttpStatus.UNAUTHORIZED;
            case GrpcStatus.PERMISSION_DENIED: return HttpStatus.FORBIDDEN;
            default: return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}