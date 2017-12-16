package pl.inz.ctscan.core.controller;

import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.inz.ctscan.core.service.UserService;
import pl.inz.ctscan.core.utils.ResultProducer;
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
