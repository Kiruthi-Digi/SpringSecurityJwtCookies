package com.digilab.springboottokencookies.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digilab.springboottokencookies.models.Role;
import com.digilab.springboottokencookies.models.User;
import com.digilab.springboottokencookies.repos.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Log the user details
        System.out.println("User details: {}" + user.getId());
        System.out.println("User details: {}" + user.getUsername());
        System.out.println("User details: {}" + user.getEmail());
        System.out.println("User details: {}" + user.getPassword());
        System.out.print("User roles: ");
        for (Role role : user.getRoles()) {
            System.out.print(role.getName() + " ");
        }
        System.out.println();
        logger.info("User details: {}", user);
        return UserDetailsImpl.build(user);
    }

}