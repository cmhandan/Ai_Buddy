package help.buddy.ai.backend.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

public class UserResponse {
    private long id;
    private String name;
    private String email;
    private Boolean status;
    private String token;

    public UserResponse(long id, String name, String email, Boolean status, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
