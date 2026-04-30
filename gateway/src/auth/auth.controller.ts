import { Body, Controller, HttpCode, HttpStatus, Inject, OnModuleInit, Post, Res } from "@nestjs/common";
import { AuthService } from "./auth.service";
import type { ClientGrpc } from "@nestjs/microservices";
import { LoginDTO } from "./dto/login.dto";
import { firstValueFrom } from "rxjs";
import { RegisterDTO } from "./dto/register.dto";
import type { Response } from "express";
import * as jwt from 'jsonwebtoken';
import { Http } from "src/common/http/http.handle.response";
import { Logger } from "@nestjs/common";

@Controller('auth')
export class AuthController implements OnModuleInit {
    private authService: AuthService;
    private readonly logger = new Logger(AuthController.name);

    constructor(@Inject('AUTH_PACKAGE') private readonly client: ClientGrpc) {}

    onModuleInit() {
        this.authService = this.client.getService<AuthService>('UserService');
    }

    @Post('login')
    @HttpCode(HttpStatus.OK)
    async login(
        @Body() loginDTO: LoginDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const observable = this.authService.LoginUser({
                email: loginDTO.email,
                password: loginDTO.password
            });

            const data = await firstValueFrom(observable);

            const decoded = jwt.verify(data.jwt, process.env.JWT_SECRET as string) as jwt.JwtPayload;

            //probably i can handle this better, but for now this will do
            if (!decoded || typeof decoded.exp !== 'number')
                throw new Error('El microservicio Auth devolvió un token inválido');
            
            const expiresAt = new Date(decoded.exp * 1000);

            response.cookie('auth_jwt', data.jwt, {
                httpOnly: true,
                secure: process.env.NODE_ENV === 'production',
                sameSite: 'lax',
                maxAge: expiresAt.getTime() - Date.now()
            });

            this.logger.log(`Usuario ${loginDTO.email} loggeado correctamente, su cookie JWT expirará en ${expiresAt.toISOString()}`);
            
            return data;
        } catch (err) {
            this.logger.error(`Error al loggear el usuario ${loginDTO.email}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }

    @Post('register')
    @HttpCode(HttpStatus.CREATED) //this is a default value for nestjs, but i put it for more clarity
    async register(
        @Body() registerDTO: RegisterDTO,
        @Res({ passthrough: true }) response: Response
    ) {
        try {
            const observable = this.authService.RegisterUser({
                email: registerDTO.email,
                password: registerDTO.password
            });

            const data = await firstValueFrom(observable);

            this.logger.log(`Usuario ${registerDTO.email} registrado correctamente`);

            return data;
        } catch (err) {
            this.logger.error(`Error al registrar el usuario ${registerDTO.email}: ${err}`);

            return Http.handleHttpErrorResponse(err, response);
        }
    }
}