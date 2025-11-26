package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        User user = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                List.of()
        );
    }

}
