package io.github.sporklibrary.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Exception that is thrown when binding fails.
 */
public class BindException extends RuntimeException {

    public BindException(Class<? extends Annotation> annotation, String message) {
        super(annotation.getSimpleName() + " failed: " + message);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, String reason, Exception cause) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + ": " + reason, cause);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, String reason) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + ": " + reason);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, Field field, String reason) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + " at field '" + field.getName() + "': " + reason);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, Field field, String reason, Exception cause) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + " at field '" + field.getName() + "': " + reason, cause);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, Method method, String reason) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + " at method '" + method.getName() + "': " + reason);
    }

    public BindException(Class<? extends Annotation> annotation, Class<?> parent, Method method, String reason, Exception cause) {
        super(annotation.getSimpleName() + " failed for " + parent.getSimpleName() + " at method '" + method.getName() + "': " + reason, cause);
    }
}
