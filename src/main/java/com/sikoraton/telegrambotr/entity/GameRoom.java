package com.sikoraton.telegrambotr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Entity
@Data
public class GameRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "creator_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "room_creator_to_user"))
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                mappedBy = "gameRoom")
    private Set<Record> records;

}
