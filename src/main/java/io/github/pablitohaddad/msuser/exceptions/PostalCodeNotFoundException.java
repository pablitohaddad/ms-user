package io.github.pablitohaddad.msuser.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class PostalCodeNotFoundException extends DataIntegrityViolationException {
    public PostalCodeNotFoundException(String msg) {
        super(msg);
    }
}
