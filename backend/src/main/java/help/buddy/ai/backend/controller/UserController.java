package help.buddy.ai.backend.controller;

import help.buddy.ai.backend.dto.UserLoginRequest;
import help.buddy.ai.backend.dto.UserResponse;
import help.buddy.ai.backend.entity.User;
import help.buddy.ai.backend.services.UserService;
import help.buddy.ai.backend.utility.Apiresponse;
import help.buddy.ai.backend.utility.Apistatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    UserService userService;
    
    @PostMapping(path = "register")
    public ResponseEntity<Apiresponse<UserResponse>> register(@RequestBody User user) {
        UserResponse userResponse = null;
        try {
            userResponse = userService.registerUser(user);
        } catch (Exception exception) {
            // error resopnse
            return new ResponseEntity<>(new Apiresponse<>(null, Apistatus.FAILED, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Apiresponse<>(userResponse, Apistatus.SUCCESS, "User Registered Successfully"), HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public ResponseEntity<Apiresponse<UserResponse>> login(@RequestBody UserLoginRequest user) {
        UserResponse userResponse = null;
        try {
            userResponse = userService.loginUser(user);
        } catch (Exception exception) {
            // error resopnse
            return new ResponseEntity<>(new Apiresponse<>(null, Apistatus.FAILED, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Apiresponse<>(userResponse, Apistatus.SUCCESS, "User LoggedIn Successfully"), HttpStatus.OK);
    }
        
    @GetMapping(path = "view")
    public String showView() {
        return "Mount Everest";
    }
}
