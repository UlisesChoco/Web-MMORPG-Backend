package com.chocolatada.auth.service.jpa.impl;

import com.chocolatada.auth.repository.IUserRepository;
import com.chocolatada.auth.security.JwtTokenProvider;
import com.chocolatada.auth.security.VerificationTokenData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.chocolatada.auth.entity.UserEntity;
import com.chocolatada.auth.entity.UserStatus;
import com.chocolatada.auth.exception.InvalidCredentialsException;
import com.chocolatada.auth.exception.InvalidUserDataException;
import com.chocolatada.auth.exception.UserAlreadyExistsException;
import com.chocolatada.auth.exception.UserNotActiveException;
import com.chocolatada.auth.security.PasswordEncoderService;
import com.chocolatada.auth.service.jpa.IUserService;
import com.chocolatada.auth.validator.UserValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final UserValidator userValidator;

    private final PasswordEncoderService passwordEncoderService;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserEntity registerUser(String email, String password)
        throws InvalidUserDataException, UserAlreadyExistsException {
        userValidator.validateEmail(email);
        userValidator.validatePassword(password);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(
                "Ya existe un usuario registrado con el email: " + email
            );
        }

        String encodedPassword = passwordEncoderService.encode(password);
        UserEntity newUser = UserEntity.builder()
            .email(email)
            .password(encodedPassword)
            .status(UserStatus.PENDING)
            .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity loginUser(String email, String password)
        throws InvalidUserDataException, InvalidCredentialsException, UserNotActiveException {
        userValidator.validateEmail(email);
        userValidator.validatePassword(password);

        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new InvalidCredentialsException(
                "Las credenciales proporcionadas son inválidas"
            ));

        if (!passwordEncoderService.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException(
                "Las credenciales proporcionadas son inválidas"
            );
        }

        if (user.getStatus() == UserStatus.PENDING) {
            throw new UserNotActiveException(
                "El usuario aún no ha verificado su correo electrónico"
            );
        }

        if (user.getStatus() == UserStatus.BANNED) {
            throw new UserNotActiveException(
                "Este usuario ha sido baneado y no tiene acceso al sistema"
            );
        }

        return user;
    }

    @Override
    public UserEntity updateUserStatus(Long userId, UserStatus newStatus) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Usuario no encontrado con ID: " + userId
            ));

        user.setStatus(newStatus);

        return userRepository.save(user);
    }

    @Override
    public void markEmailAsVerified(String token) throws Exception {
        try {
            log.info("Intento de verificación de email con token: " + token);

            VerificationTokenData tokenData = jwtTokenProvider.validateVerificationToken(token);
            Long userId = tokenData.getUserId();
            String email = tokenData.getEmail();

            log.info("Token válido para usuario ID: "+ userId +", email: " + email);

            updateUserStatus(userId, UserStatus.ACTIVE);

            log.info("Usuario ID: "+ userId +" marcado como ACTIVE");
        } catch (Exception e) {
            log.error("Error durante la verificación de email con token: "+ token, e);
            throw e;
        }
    }
}