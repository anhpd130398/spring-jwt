package demo.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "userbo")
public class UserBO {
    @Id
    private int id;
    private String username;
    private String password;
    private String token;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "role_id")
    private String roleId;

}
