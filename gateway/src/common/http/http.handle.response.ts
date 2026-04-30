import { Response } from "express";
import { GrpcStatusCode } from "../grpc/grpc.status.code";

export class Http {
    static handleHttpErrorResponse(error: any, response: Response) {
        const statusCode = GrpcStatusCode.grpcToHttp(error.code);
        
        response.status(statusCode);

        return { "code": statusCode, "details": error.details };
    }
}