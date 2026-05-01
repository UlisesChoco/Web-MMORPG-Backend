import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import cookieParser from 'cookie-parser';
import * as dotenv from 'dotenv';

dotenv.config();

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.use(cookieParser());

  app.enableCors({
    origin: [
       "http://localhost:6767",
       "http://ulisesjustosaucedo.tech",
       "https://ulisesjustosaucedo.tech"
    ],
    credentials: true
  });

  await app.listen(process.env.PORT ?? 3000);
}
bootstrap();
