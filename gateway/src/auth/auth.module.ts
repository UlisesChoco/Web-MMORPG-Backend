import { Module } from "@nestjs/common";
import { AuthController } from "./auth.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'AUTH_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'auth:9090', //i have to change this later bc of docker, but it's ok for now
                    package: 'Auth',
                    protoPath: './src/auth/proto/user.proto',
                }
            }
        ])
    ],
    controllers: [AuthController],
    providers: []
})
export class AuthModule {}
