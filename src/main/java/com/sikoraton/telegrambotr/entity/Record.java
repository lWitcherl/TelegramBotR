package com.sikoraton.telegrambotr.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Record implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_room_id",
                foreignKey = @ForeignKey(name = "game_room_to_record"))
    private GameRoom gameRoom;

    private String task;

    public Record() {
    }

    public Record(String task) {
        this.task = task;
    }
}
