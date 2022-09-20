package com.sikoraton.telegrambotr.service.imp;

import java.util.regex.Pattern;

import com.sikoraton.telegrambotr.bot.handlers.keyboards.MainMenuKeyboard;
import com.sikoraton.telegrambotr.bot.handlers.keyboards.StartKeyboard;
import com.sikoraton.telegrambotr.entity.Record;
import com.sikoraton.telegrambotr.enums.StatusEnums;
import com.sikoraton.telegrambotr.repository.GameRoomRepository;
import com.sikoraton.telegrambotr.repository.RecordRepository;
import com.sikoraton.telegrambotr.service.DataFromUserService;
import com.sikoraton.telegrambotr.util.BotMessageText;
import com.sikoraton.telegrambotr.util.I10nHandler;
import com.sikoraton.telegrambotr.util.SessionCache;
import com.sikoraton.telegrambotr.util.UserCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@Getter @Setter
@Transactional
public class DataFromUserServiceImpl implements DataFromUserService {
    private SessionCache sessionCache;
    private GameRoomRepository gameRoomRepository;
    private RecordRepository recordRepository;
    private BotMessageText botMessageText;
    private MainMenuKeyboard mainMenuKeyboard;
    private StartKeyboard startKeyboard;

    @Autowired
    public DataFromUserServiceImpl(SessionCache sessionCache,
                                   GameRoomRepository gameRoomRepository,
                                   BotMessageText botMessageText,
                                   RecordRepository recordRepository,
                                   MainMenuKeyboard mainMenuKeyboard,
                                   StartKeyboard startKeyboard) {
        this.sessionCache = sessionCache;
        this.gameRoomRepository = gameRoomRepository;
        this.botMessageText = botMessageText;
        this.recordRepository = recordRepository;
        this.mainMenuKeyboard = mainMenuKeyboard;
        this.startKeyboard = startKeyboard;
    }

    @Override
    public BotApiMethodMessage processingData(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        BotApiMethodMessage sendMessage = null;
        if(StatusEnums.ADDING_ROW == userCache.getStatus())
            sendMessage = addingRowProcessing(message, userCache);
        else if (StatusEnums.JOIN_ROOM == userCache.getStatus())
            sendMessage = joinToRoomProcessing(message, userCache);
        userCache.setStatus(null);
        return sendMessage;
    }

    private BotApiMethodMessage addingRowProcessing(Message message, UserCache userCache){
        Record record = new Record();
        record.setTask(message.getText());
        record.setGameRoom(gameRoomRepository.getReferenceById(userCache.getRoomId()));
        recordRepository.save(record);
        return SendMessage.builder().chatId(message.getChatId())
                .text(I10nHandler.getTextByLanguage(botMessageText.getRowadded(), userCache.getLanguageCode()))
                .replyMarkup(mainMenuKeyboard.buildKeyboard(userCache.getLanguageCode()))
                .build();
    }

    private BotApiMethodMessage joinToRoomProcessing(Message message, UserCache userCache){
        Pattern pattern = Pattern.compile("\\d+");
        String text = I10nHandler.getTextByLanguage(botMessageText.getRoomnotfound(), userCache.getLanguageCode());
        ReplyKeyboardMarkup keyboardMarkup = startKeyboard.buildKeyboard(userCache.getLanguageCode());
        if (pattern.matcher(message.getText()).matches()) {
            Long id = Long.parseLong(message.getText());
            if (gameRoomRepository.existsById(id)) {
                userCache.setRoomId(Long.parseLong(message.getText()));
                text = String.format(
                        I10nHandler.getTextByLanguage(botMessageText.getJoincomplicte(), userCache.getLanguageCode()),
                        id);
                keyboardMarkup = mainMenuKeyboard.buildKeyboard(userCache.getLanguageCode());
            }
        }
        return SendMessage.builder().chatId(message.getChatId())
                .text(text).replyMarkup(keyboardMarkup)
                .build();
    }
}
