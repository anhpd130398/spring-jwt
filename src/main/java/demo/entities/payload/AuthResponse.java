package demo.entities.payload;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String refreshToken;
}
