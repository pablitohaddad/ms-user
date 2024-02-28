package io.github.pablitohaddad.msuser.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String msg) {
        super(msg);
    }
}
