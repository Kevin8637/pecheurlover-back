package com.pecheur_lover.pecheurlover.services;

import com.pecheur_lover.pecheurlover.daos.UserDao;
import com.pecheur_lover.pecheurlover.entities.CustomUserDetails;
import com.pecheur_lover.pecheurlover.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        return new CustomUserDetails(user);
    }
}
