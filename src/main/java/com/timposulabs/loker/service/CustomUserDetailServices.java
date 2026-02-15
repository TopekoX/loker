package com.timposulabs.loker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.timposulabs.loker.entity.Users;
import com.timposulabs.loker.repository.UsersRepository;
import com.timposulabs.loker.util.CustomUserDetail;

@Service
public class CustomUserDetailServices implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new CustomUserDetail(users);
    }    
}
