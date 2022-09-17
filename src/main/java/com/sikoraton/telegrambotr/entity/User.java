package com.sikoraton.telegrambotr.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    private Long id;
    private String firstName;
    private String userName;

}
