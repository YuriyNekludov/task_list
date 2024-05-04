package yuriy.spring.exception;

public class PasswordsMismatchingException extends RuntimeException {

    public PasswordsMismatchingException(String message) {
        super(message);
    }
}
