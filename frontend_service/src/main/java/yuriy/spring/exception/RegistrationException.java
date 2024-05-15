package yuriy.spring.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistrationException extends RuntimeException {

    private final List<String> errors;

    public RegistrationException(List<String> errors) {
        this.errors = errors;
    }
}
