package org.acme.exceptions;

public class TokenException extends Exception{

    public TokenException(String errorMessage) {
        super(errorMessage);
    }
}
