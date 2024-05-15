package yuriy.spring.security.custom_handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import yuriy.spring.util.ErrorResponseUtil;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ErrorResponseUtil errorResponseUtil;

    @Override
    @SneakyThrows
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        errorResponseUtil.sendErrorResponse(response,
                "errors.403.forbidden",
                HttpServletResponse.SC_FORBIDDEN);
    }
}
