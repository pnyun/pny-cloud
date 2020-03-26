package com.pny.core.exception;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.pny.exception.PnyYunException;


/**
 * Exception associated with the not found http status.
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends PnyYunException {

    public ResourceNotFoundException(String message, Object... arguments) {
        super(NO_CONTENT.toString(), NO_CONTENT.name() + message);
    }

}
