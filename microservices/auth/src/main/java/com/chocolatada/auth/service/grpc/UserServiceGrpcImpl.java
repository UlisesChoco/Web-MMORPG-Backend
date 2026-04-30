package com.chocolatada.auth.service.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chocolatada.auth.entity.UserEntity;
import com.chocolatada.auth.exception.InvalidCredentialsException;
import com.chocolatada.auth.exception.InvalidUserDataException;
import com.chocolatada.auth.exception.UserAlreadyExistsException;
import com.chocolatada.auth.exception.UserNotActiveException;
import com.chocolatada.auth.grpc.LoginUserRequest;
import com.chocolatada.auth.grpc.LoginUserResponse;
import com.chocolatada.auth.grpc.RegisterUserRequest;
import com.chocolatada.auth.grpc.RegisterUserResponse;
import com.chocolatada.auth.grpc.UserServiceGrpc;
import com.chocolatada.auth.security.JwtTokenProvider;
import com.chocolatada.auth.service.jpa.IUserService;
import com.chocolatada.auth.service.mail.IMailService;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {

    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final IMailService mailService;

    @Value("${app.verification.url:http://localhost:8080/api/auth/verify}")
    private String verificationBaseUrl;

    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseObserver) {
        try {
            UserEntity newUser = userService.registerUser(request.getEmail(), request.getPassword());

            String verificationToken = jwtTokenProvider.generateVerificationToken(
                newUser.getId(), 
                newUser.getEmail()
            );

            String verificationLink = verificationBaseUrl + "?token=" + verificationToken;

            /*
            lo coloco en un try catch aparte para no afectar el flujo principal
            si se registra correctamente pero falla el envio del correo,
            el usuario igual queda registrado y puede solicitar un nuevo correo de verificacion
             */
            try {
                mailService.sendVerificationEmail(newUser.getEmail(), verificationLink);
                log.info("Correo de verificación enviado a: "+ newUser.getEmail());
            } catch (Exception e) {
                log.error("Error al enviar correo de verificación: "+ e.getMessage());
            }

            RegisterUserResponse response = RegisterUserResponse.newBuilder()
                .setMessage("Usuario registrado exitosamente. Verifica tu correo electrónico")
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (InvalidUserDataException e) {
            log.error("Datos inválidos en intento de registro para el email: "+ request.getEmail());
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asException()
            );
        } catch (UserAlreadyExistsException e) {
            log.error("Intento de registro con email ya existente: "+ request.getEmail());
            responseObserver.onError(
                Status.ALREADY_EXISTS
                    .withDescription(e.getMessage())
                    .asException()
            );
        } catch (Exception e) {
            log.error("Error inesperado en registerUser: "+ e.getMessage(), e);
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asException()
            );
        }
    }

    @Override
    public void loginUser(LoginUserRequest request, StreamObserver<LoginUserResponse> responseObserver) {
        try {
            UserEntity user = userService.loginUser(request.getEmail(), request.getPassword());

            String jwt = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

            LoginUserResponse response = LoginUserResponse.newBuilder()
                .setMessage("Login exitoso")
                .setJwt(jwt)
                .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (InvalidUserDataException e) {
            log.error("Datos inválidos en intento de login para el email: "+ request.getEmail());
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asException()
            );
        } catch (InvalidCredentialsException e) {
            log.error("Credenciales inválidas para el email: "+ request.getEmail());
            responseObserver.onError(
                Status.UNAUTHENTICATED
                    .withDescription(e.getMessage())
                    .asException()
            );
        } catch (UserNotActiveException e) {
            log.error("Intento de login con usuario no activo: "+ request.getEmail());
            responseObserver.onError(
                Status.PERMISSION_DENIED
                    .withDescription(e.getMessage())
                    .asException()
            );
        } catch (Exception e) {
            log.error("Error inesperado en loginUser: "+ e.getMessage(), e);
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription("Error interno del servidor")
                    .asException()
            );
        }
    }
}
