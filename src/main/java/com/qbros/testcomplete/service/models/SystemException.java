package com.qbros.testcomplete.service.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Map<String, Object> properties = new HashMap<>();
    private final ErrorCode errorCode;

    public SystemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SystemException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Long, redundant stack traces not only do not help you, but they are also a waste of time and resources.
     * When rethrowing exceptions, call a static wrap method instead of the exception’s constructor.
     * The wrap method decides when to nest exceptions and when to just return the original instance.
     *
     * @param exception the exception we want to wrap
     * @param errorCode the error code we want to wrap
     * @return wrapped exception
     */
    public static SystemException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof SystemException) {
            SystemException se = (SystemException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new SystemException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new SystemException(exception.getMessage(), exception, errorCode);
        }
    }

    /**
     * @param exception exception the exception we want to wrap
     * @return wrapped exception
     * @see SystemException#wrap(Throwable, ErrorCode)
     */
    public static SystemException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String name) {
        return (T) properties.get(name);
    }

    /**
     * You’ll also need to add a generic setter method following the fluent interface pattern.
     * <p>
     * Throwing exceptions, with relevant data, will now look something like this.
     * <pre>
     *  {@code
     *  throw new SystemException(ValidationCode.VALUE_TOO_SHORT)
     *   .set("field", field)
     *   .set("value", value)
     *   .set("min-length", MIN_LENGTH);
     *  }
     * </pre>
     *
     * @param name  name of the property
     * @param value value of the property
     * @return SystemException
     */
    public SystemException setProperty(String name, Object value) {
        properties.put(name, value);
        return this;
    }

}
