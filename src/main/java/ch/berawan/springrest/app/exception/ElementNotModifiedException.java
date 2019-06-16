package ch.berawan.springrest.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ElementNotModifiedException extends RuntimeException {
    public ElementNotModifiedException() {
        super();
    }

    public ElementNotModifiedException(final String message) {
        super(message);
    }

    public ElementNotModifiedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElementNotModifiedException(final Throwable cause) {
        super(cause);
    }

    public ElementNotModifiedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writeableStacktrace) {
        super(message, cause, enableSuppression, writeableStacktrace);
    }
}
