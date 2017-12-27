package pl.inz.ctscan.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.inz.ctscan.core.service.UserService;
import pl.inz.ctscan.core.utils.response.ResultProducer;
import pl.inz.ctscan.model.ApplicationUser;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Map<String, Object> signUp(@RequestBody ApplicationUser user) throws Exception {
        if(userService.correctUserData(user) && !userService.userExist(user.getEmail())) {
            userService.signUp(user);

            return ResultProducer.createResponse(true);
        } else {
            throw new Exception("User exists");
        }
    }
}
