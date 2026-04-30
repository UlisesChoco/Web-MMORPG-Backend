import { Module } from "@nestjs/common";
import { PlayerClassController } from "../controller/player-class.controller";
import { ClientsModule, Transport } from "@nestjs/microservices";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'PLAYER_CLASS_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'class:9091',
                    package: 'Class',
                    protoPath: 'src/class/proto/player_class.proto'
                }
            }
        ])
    ],
    controllers: [PlayerClassController]
})
export class PlayerClassModule {

}
