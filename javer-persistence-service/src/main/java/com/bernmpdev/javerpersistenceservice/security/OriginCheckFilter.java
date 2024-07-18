package com.bernmpdev.javerpersistenceservice.security;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OriginCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String originHeader = httpRequest.getHeader("X-Origin-Header");
        if ("javer-proxy-service".equals(originHeader)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

    @Override
    public void destroy() {
    }
}
