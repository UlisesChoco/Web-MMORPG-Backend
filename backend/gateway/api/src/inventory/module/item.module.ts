import { Module } from "@nestjs/common";
import { ClientsModule, Transport } from "@nestjs/microservices";
import { ItemController } from "../controller/item.controller";

@Module({
    imports: [
        ClientsModule.register([
            {
                name: 'ITEM_PACKAGE',
                transport: Transport.GRPC,
                options: {
                    url: 'localhost:9093',
                    package: 'Inventory',
                    protoPath: 'src/inventory/proto/item.proto'
                }
            }
        ])
    ],
    controllers: [ItemController]
})
export class ItemModule {}