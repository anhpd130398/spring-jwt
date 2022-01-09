package demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
public class Role {
    @Id
    private int id;
    private String name;
}
