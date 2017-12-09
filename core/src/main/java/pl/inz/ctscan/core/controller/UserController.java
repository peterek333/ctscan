package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.UserService;
import pl.inz.ctscan.model.ApplicationUser;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public void signUp(@RequestBody ApplicationUser user) {
        userService.signUp(user);
    }

//    @GetMapping("/signup")
//    public String getSignupView() {
//        return "signup";
//    }
}
