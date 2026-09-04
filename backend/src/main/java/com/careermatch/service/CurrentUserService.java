package com.careermatch.service;

import com.careermatch.entity.User;
import com.careermatch.exception.UnauthorizedException;
import com.careermatch.repository.UserRepository;
import com.careermatch.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userRepository.findById(userDetails.getId());
        }

        return userRepository.findByEmail(authentication.getName());
    }

    public User getCurrentUser() {
        return getCurrentUserOptional()
                .orElseThrow(() -> new UnauthorizedException("User is not authenticated"));
    }
}
