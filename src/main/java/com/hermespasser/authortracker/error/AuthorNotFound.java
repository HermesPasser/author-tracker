package com.hermespasser.authortracker.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFound extends Throwable {
    public AuthorNotFound(Long id){
        super("Not author could be found with this id " + id);
    }
}
