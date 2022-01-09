package demo.entities.payload;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String roleId;

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
