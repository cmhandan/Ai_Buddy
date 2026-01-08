package help.buddy.ai.backend.services.implementations;

import help.buddy.ai.backend.config.Security;
import help.buddy.ai.backend.dto.UserLoginRequest;
import help.buddy.ai.backend.dto.UserResponse;
import help.buddy.ai.backend.entity.User;
import help.buddy.ai.backend.repository.UserRepository;
import help.buddy.ai.backend.services.JWTService;
import help.buddy.ai.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    Security security;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTService jwtService;

    @Override
    public UserResponse registerUser(User user) {
        String name = user.getName().trim();
        String email = user.getEmail().trim();
        String password = user.getPassword();
        boolean status = user.getStatus();
        if (userRepository.existsByEmail(email)) {
           throw new RuntimeException("User Already Existed");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setStatus(status);
        newUser.setName(name);
        newUser.setCreatedAt(new Date());
        newUser.setPassword(security.passwordEncoder().encode(password));
        userRepository.save(newUser);
        String token = jwtService.createToken(newUser);

        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getStatus(), token);
        return userResponse;
    }

    @Override
    public UserResponse loginUser(UserLoginRequest user) {
        String email = user.getEmail().trim();
        User existedUser = userRepository.findByEmail(user.getEmail());
        String password = user.getPassword();
        if (existedUser == null || !security.verifyPassword(user.getPassword(), existedUser.getPassword())) {
            throw new RuntimeException("User Not Found with us...");
        }
        String token = jwtService.createToken(existedUser);
        UserResponse userResponse = new UserResponse(existedUser.getId(), existedUser.getName(), existedUser.getEmail(), existedUser.getStatus(), token);
        return userResponse;        
    }
}
