package yuriy.spring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import yuriy.spring.dto.ErrorMessage;

@RequiredArgsConstructor
@Component
public class ErrorResponseUtil {

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendErrorResponse(final HttpServletResponse response,
                                  String messageKey,
                                  int statusCode) {
        var locale = LocaleContextHolder.getLocale();
        var message = messageSource.getMessage(messageKey, new Object[0], locale);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorMessage(message)));
    }
}
