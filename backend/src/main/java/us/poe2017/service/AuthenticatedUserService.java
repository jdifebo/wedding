package us.poe2017.service;

import us.poe2017.security.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService implements AuditorAware<String> {

    public String getCurrentAuditor() {
        return getCurrentUsername();
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((CustomUserDetails) authentication.getPrincipal()).getUsername();
    }
}
