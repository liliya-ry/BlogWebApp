package com.example.BlogWebApp.logging;

import jakarta.servlet.http.*;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LogManager.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long startTime = (long) request.getAttribute("startTime");
        long execTime = endTime - startTime;

        int status = response.getStatus();
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();

        String user = (String) request.getAttribute("user");
        String logMsg = String.format("User: %s, Method: %s, URI: %s, Status: %s, Execution Time: %s ms", user, httpMethod, uri, status, execTime);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoggingInfo loggingInfoAnn = handlerMethod.getMethodAnnotation(LoggingInfo.class);
        if (loggingInfoAnn != null)
            logMsg = String.format(loggingInfoAnn.value(), logMsg);

        LOGGER.info(logMsg);
    }
}
