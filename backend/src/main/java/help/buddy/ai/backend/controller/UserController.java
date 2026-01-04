package help.buddy.ai.backend.controller;

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
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(path = "register")
    public ResponseEntity<Apiresponse<UserResponse>> greet(@RequestBody User user) {
        UserResponse userResponse = null;
        try {
            userResponse = userService.registerUser(user);
        } catch (Exception exception) {
            // error resopnse
            return new ResponseEntity<>(new Apiresponse<>(null, Apistatus.FAILED, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Apiresponse<>(userResponse, Apistatus.SUCCESS, "User Registered Successfully", token), HttpStatus.CREATED);
    }
        
    @GetMapping(path = "view")
    public String showView() {
        return "Mount Everest";
    }
}
