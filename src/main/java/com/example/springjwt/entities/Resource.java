package com.example.springjwt.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    private int id;
    private String authName;
}
