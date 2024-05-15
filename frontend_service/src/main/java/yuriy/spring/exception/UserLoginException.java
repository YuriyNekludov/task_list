package yuriy.spring.exception;

public class UserLoginException extends RuntimeException {

    public UserLoginException(String message) {
        super(message);
    }
}
