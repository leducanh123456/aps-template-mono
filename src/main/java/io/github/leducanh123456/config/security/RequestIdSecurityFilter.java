package io.github.leducanh123456.config.security;

import io.github.leducanh123456.constant.AppConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RequestIdSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestId = UUID.randomUUID().toString();
        String rawRefNo = request.getHeader(AppConstants.REF_NO);
        String refNo;
        try {
            refNo = UUID.fromString(rawRefNo).toString();
        } catch (Exception e) {
            refNo = UUID.randomUUID().toString();
        }

        // Gắn requestId và refNo vào Authentication nếu đã đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && !(authentication.getPrincipal() instanceof String
                && "anonymousUser".equals(authentication.getPrincipal()))) {

            Map<String, Object> details = new HashMap<>();
            details.put(AppConstants.REQUEST_ID, requestId);
            details.put(AppConstants.REF_NO, refNo);

            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );
            newAuth.setDetails(details);

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        filterChain.doFilter(request, response);
    }
}
