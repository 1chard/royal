package com.royal.validation;

/**
 *
 * @author suporte
 */
public class MustHasFailedException extends RuntimeException {

    public MustHasFailedException() {
    }

    public MustHasFailedException(String message) {
	super(message);
    }

    public MustHasFailedException(String message, Throwable cause) {
	super(message, cause);
    }

    public MustHasFailedException(Throwable cause) {
	super(cause);
    }
    
}
