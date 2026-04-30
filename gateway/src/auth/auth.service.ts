import { Observable } from "rxjs";
import { LoginUserRequest } from "./interfaces/login/login-user-request.interface";
import { LoginUserResponse } from "./interfaces/login/login-user-response.interface";
import { RegisterUserRequest } from "./interfaces/register/register-user-request.interface";
import { RegisterUserResponse } from "./interfaces/register/register-user-response.interface";

export interface AuthService {
    LoginUser(request: LoginUserRequest): Observable<LoginUserResponse>;
    RegisterUser(request: RegisterUserRequest): Observable<RegisterUserResponse>;
}