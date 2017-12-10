package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.UserService;
import pl.inz.ctscan.model.ApplicationUser;
import pl.inz.ctscan.model.validator.UserValidator;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public void signUp(@RequestBody ApplicationUser user) throws Exception {
        if(userService.correctUserData(user) && !userService.userExist(user.getEmail())) {
            userService.signUp(user);
        } else {
            throw new Exception("User exists");
        }
    }
}
