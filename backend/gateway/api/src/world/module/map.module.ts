import { ClientsModule, Transport } from "@nestjs/microservices";
import { Module } from "@nestjs/common";
import { MapController } from "../controller/map.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'MAP_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'localhost:9097',
                    package: 'World',
                    protoPath: 'src/world/proto/map.proto'
                }
            }
        ])
    ],
    controllers: [MapController]
})
export class MapModule {}