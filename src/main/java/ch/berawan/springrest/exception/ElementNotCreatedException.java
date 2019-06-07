package ch.berawan.springrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ElementNotCreatedException extends RuntimeException {
    public ElementNotCreatedException() {
        super();
    }

    public ElementNotCreatedException(final String message) {
        super(message);
    }

    public ElementNotCreatedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElementNotCreatedException(final Throwable cause) {
        super(cause);
    }

    public ElementNotCreatedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writeableStacktrace) {
        super(message, cause, enableSuppression, writeableStacktrace);
    }
}
