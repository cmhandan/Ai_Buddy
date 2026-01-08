package help.buddy.ai.backend.services;

import help.buddy.ai.backend.dto.UserLoginRequest;
import help.buddy.ai.backend.dto.UserResponse;
import help.buddy.ai.backend.entity.User;

public interface UserService {
    public UserResponse registerUser(User user);
    public UserResponse loginUser(UserLoginRequest user);
    
    
}
