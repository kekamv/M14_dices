package dices.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthRequest {

    @NotNull(message = "`username` field is mandatory")
    @Size(min = 3, message = "`username` must be at least 3 characters long")
    private String username;
    @NotNull(message = "`password` field is mandatory")
    @Size(min = 8, message = "`password` must be at least 8 characters long")
    private String password;

    private AuthRequest(){
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
