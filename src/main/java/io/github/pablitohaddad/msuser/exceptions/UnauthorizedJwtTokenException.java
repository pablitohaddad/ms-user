package io.github.pablitohaddad.msuser.exceptions;

public class UnauthorizedJwtTokenException extends RuntimeException{
    public UnauthorizedJwtTokenException(String msg) {
        super(msg);
    }
}
