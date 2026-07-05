import {
  Injectable,
  NestMiddleware,
  UnauthorizedException,
} from '@nestjs/common';
import type { Request, Response, NextFunction } from 'express';
import * as jwt from 'jsonwebtoken';

@Injectable()
export class AuthMiddleware implements NestMiddleware {
  use(req: Request, res: Response, next: NextFunction) {
    if (!req.cookies['auth_jwt'])
      return next(
        new UnauthorizedException('No existe token de autenticación'),
      );

    try {
      const token = req.cookies['auth_jwt'];

      const decodedToken = jwt.verify(token, process.env.JWT_SECRET as string);

      (req as any).user = decodedToken;

      next();
    } catch (err) {
      if (err instanceof jwt.JsonWebTokenError)
        return next(new UnauthorizedException('Token inválido'));

      if (err instanceof jwt.TokenExpiredError)
        return next(new UnauthorizedException('El token ha expirado'));

      return next(new UnauthorizedException('Error de autenticación'));
    }
  }
}
