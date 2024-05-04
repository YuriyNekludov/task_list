package yuriy.spring.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(

        @NotBlank(message = "{authentication.registration.errors.username_is_blank}")
        @Size(min = 4, max = 64, message = "{authentication.registration.errors.username_size_is_invalid}")
        String username,

        @NotBlank(message = "{authentication.registration.errors.email_is_blank}")
        @Email(message = "{authentication.registration.errors.email_format_invalid}")
        @Size(min = 7, max = 128, message = "{authentication.registration.errors.email_size_is_invalid}")
        String email,

        @NotBlank(message = "{authentication.registration.errors.password_is_blank}")
        @Size(min = 3, message = "{authentication.registration.errors.password_size_is_invalid}")
        String password,

        @NotBlank(message = "{authentication.registration.errors.password_confirmation_is_blank}")
        @Size(min = 3, message = "{authentication.registration.errors.password_confirmation_size_is_invalid}")
        String passwordConfirmation) {

}
