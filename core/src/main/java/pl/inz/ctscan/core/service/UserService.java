package pl.inz.ctscan.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.inz.ctscan.db.ApplicationUserRepository;
import pl.inz.ctscan.model.ApplicationUser;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        applicationUserRepository.insert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email);

        if(applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(applicationUser.getEmail(), applicationUser.getPassword(), Collections.emptyList());
    }
}
