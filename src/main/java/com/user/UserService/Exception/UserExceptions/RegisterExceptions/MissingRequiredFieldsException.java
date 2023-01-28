package com.user.UserService.Exception.UserExceptions.RegisterExceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
@NoArgsConstructor
public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String message) {

    }
}
