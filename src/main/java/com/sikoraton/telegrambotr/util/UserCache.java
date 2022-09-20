package com.sikoraton.telegrambotr.util;

import java.io.Serializable;

import com.sikoraton.telegrambotr.enums.StatusEnums;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCache implements Serializable {
    private String languageCode;
    private Long roomId;
    private StatusEnums status;
}
