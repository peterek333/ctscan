package pl.inz.ctscan.core.utils.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
