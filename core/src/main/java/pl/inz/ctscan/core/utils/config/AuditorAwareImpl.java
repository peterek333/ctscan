package pl.inz.ctscan.core.utils.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String currentUser = "system";

        SecurityContext context = SecurityContextHolder.getContext();
        if(context.getAuthentication() != null) {
            currentUser = (String) context.getAuthentication().getPrincipal();
        }

        return currentUser;
    }
}
