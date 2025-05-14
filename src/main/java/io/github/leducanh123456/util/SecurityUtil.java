package io.github.leducanh123456.util;

import io.github.leducanh123456.constant.AppConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class SecurityUtil {
    private SecurityUtil() {

    }
    public static String getRequestId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof Map<?, ?> map) {
            Object val = map.get(AppConstants.REQUEST_ID);
            return val != null ? val.toString() : null;
        }
        return null;
    }

    public static String getRefNo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof Map<?, ?> map) {
            Object val = map.get(AppConstants.REF_NO);
            return val != null ? val.toString() : null;
        }
        return null;
    }

}
