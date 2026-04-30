import { Injectable, NestMiddleware, UnauthorizedException } from "@nestjs/common";
import * as jwt from 'jsonwebtoken';

@Injectable()
export class AuthMiddleware implements NestMiddleware {
    use(req: any, res: any, next: (error?: any) => void) {
        if(!req.cookies["auth_jwt"])
            throw new UnauthorizedException('No existe token de autenticación');

        try {
            const token = req.cookies["auth_jwt"];

            const decodedToken = jwt.verify(token, process.env.JWT_SECRET as string);

            req.user = decodedToken;

            req.headers["authorization"] = `Bearer ${token}`;

            next();
        } catch (err) {
            if(err instanceof jwt.JsonWebTokenError)
                throw new UnauthorizedException('Token inválido');

            if(err instanceof jwt.TokenExpiredError)
                throw new UnauthorizedException('El token ha expirado');
        }
    }
}