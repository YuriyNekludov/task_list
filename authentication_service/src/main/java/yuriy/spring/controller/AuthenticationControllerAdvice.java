package yuriy.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import yuriy.spring.exception.DuplicateUserAttributesException;
import yuriy.spring.exception.PasswordsMismatchingException;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class AuthenticationControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex, Locale locale) {
        var problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage(
                                "errors.400.bad_request",
                                new Object[0],
                                locale));
        problemDetail.setProperty("errors",
                ex.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler({DuplicateUserAttributesException.class,
            PasswordsMismatchingException.class})
    public ResponseEntity<ProblemDetail> handleRegistrationExceptions(Exception ex,
                                                                      Locale locale) {
        var problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN,
                        messageSource.getMessage(
                                "errors.400.bad_request",
                                new Object[0],
                                locale));
        problemDetail.setProperty("errors",
                messageSource.getMessage(ex.getMessage(),
                        new Object[0],
                        locale));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

}
