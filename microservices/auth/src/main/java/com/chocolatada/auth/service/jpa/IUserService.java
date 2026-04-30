package com.chocolatada.auth.service.jpa;

import com.chocolatada.auth.entity.UserEntity;
import com.chocolatada.auth.entity.UserStatus;
import com.chocolatada.auth.exception.InvalidCredentialsException;
import com.chocolatada.auth.exception.InvalidUserDataException;
import com.chocolatada.auth.exception.UserAlreadyExistsException;
import com.chocolatada.auth.exception.UserNotActiveException;

public interface IUserService {

    UserEntity registerUser(String email, String password)
        throws InvalidUserDataException, UserAlreadyExistsException;

    UserEntity loginUser(String email, String password)
        throws InvalidUserDataException, InvalidCredentialsException, UserNotActiveException;

    UserEntity updateUserStatus(Long userId, UserStatus newStatus);

    void markEmailAsVerified(String token) throws Exception;
}
