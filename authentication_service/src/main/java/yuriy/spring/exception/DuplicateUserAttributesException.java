package yuriy.spring.exception;

public class DuplicateUserAttributesException extends RuntimeException {

    public DuplicateUserAttributesException(String message) {
        super(message);
    }
}
