package com.sikoraton.telegrambotr.bot.handlers.keyboards;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sikoraton.tbt.util.BotMenuText;
import com.sikoraton.tbt.util.I10nHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
@Getter @Setter
public class MainMenuKeyboard implements Keyboard{
    private BotMenuText menuText;

    @Autowired
    public MainMenuKeyboard(BotMenuText menuText) {
        this.menuText = menuText;
    }

    @Override
    public List<KeyboardRow> generateKeyboardRow(String languageCode) {
        String createButton = I10nHandler.getTextByLanguage(menuText.getAdded(), languageCode);
        String joinButton = I10nHandler.getTextByLanguage(menuText.getStartgame(), languageCode);
        String toStartMenu = I10nHandler.getTextByLanguage(menuText.getTostart(), languageCode);
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(createButton));
        keyboardRow1.add(new KeyboardButton(joinButton));
        keyboardRow2.add(new KeyboardButton(toStartMenu));
        return Stream.of(keyboardRow1, keyboardRow2).collect(Collectors.toList());
    }
}
