package yuriy.spring.security.custom_handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import yuriy.spring.util.ErrorResponseUtil;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ErrorResponseUtil errorResponseUtil;

    @Override
    @SneakyThrows
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        errorResponseUtil.sendErrorResponse(response,
                "errors.401.unauthorized",
                HttpServletResponse.SC_UNAUTHORIZED);
    }
}
