package com.sikoraton.telegrambotr.bot.handlers.command;

import com.sikoraton.telegrambotr.bot.handlers.keyboards.Keyboard;
import com.sikoraton.telegrambotr.mapper.UserMapper;
import com.sikoraton.telegrambotr.service.UserService;
import com.sikoraton.telegrambotr.util.BotMessageText;
import com.sikoraton.telegrambotr.util.I10nHandler;
import com.sikoraton.telegrambotr.util.SessionCache;
import com.sikoraton.telegrambotr.util.UserCache;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Getter @Setter
public class StartCommand extends BotCommand {
    private final Logger logger = LogManager.getLogger(StartCommand.class);
    private static final Marker BOT_COMMAND_MARKER = MarkerManager.getMarker("BOT_COMMAND");
    private static final Marker START_MARKER = MarkerManager.getMarker("START_COMMAND")
            .addParents(BOT_COMMAND_MARKER);

    private BotMessageText messageText;
    private SessionCache sessionCache;
    private UserService userService;
    private Keyboard startKeyboards;

    @Autowired
    public StartCommand(UserService userService,
                        @Qualifier("startKeyboard") Keyboard startKeyboards,
                        BotMessageText messageText,
                        SessionCache sessionCache) {
        super("start", "With this command you can start the Bot");
        this.userService = userService;
        this.startKeyboards = startKeyboards;
        this.messageText = messageText;
        this.sessionCache = sessionCache;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage answer = new SendMessage();
        UserCache userCache = sessionCache.getUserCache(user.getId());
        if(!userService.userIsPresent(user.getId())) {
            userService.save(UserMapper.INSTANCE.tUserToUserDto(user));
            userCache.setLanguageCode(I10nHandler.DEFAULT_LANGUAGE);
        }
        answer.setChatId(chat.getId());
        answer.setText(String.format(
                I10nHandler.getTextByLanguage(messageText.getStart(), userCache.getLanguageCode()),
                user.getFirstName()));
        answer.setReplyMarkup(startKeyboards.buildKeyboard(user.getLanguageCode()));
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            logger.error(START_MARKER, e.getMessage(), e);
        }
    }
}
