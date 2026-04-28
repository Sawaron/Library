package com.codeandpray.library.security.service;

import com.codeandpray.library.entity.User;
import com.codeandpray.library.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String firstname) throws UsernameNotFoundException {
        User user = userRepo.findByFirstname(firstname)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with firstname: " + firstname));

        return new org.springframework.security.core.userdetails.User(
                user.getFirstname(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}