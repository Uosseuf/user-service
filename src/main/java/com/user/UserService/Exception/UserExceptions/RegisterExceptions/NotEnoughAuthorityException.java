package com.user.UserService.Exception.UserExceptions.RegisterExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotEnoughAuthorityException extends RuntimeException {
}
