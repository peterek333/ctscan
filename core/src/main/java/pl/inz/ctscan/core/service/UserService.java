package pl.inz.ctscan.core.service;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import pl.inz.ctscan.db.ApplicationUserRepository;
import pl.inz.ctscan.model.ApplicationUser;
import pl.inz.ctscan.model.validator.UserValidator;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserValidator userValidator;


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

    public boolean userExist(@NotNull @NotEmpty String email) {
        return applicationUserRepository.findByEmail(email) != null;
    }

    public boolean correctUserData(ApplicationUser user) throws Exception {
        return userValidator.validate(user);
    }
}
