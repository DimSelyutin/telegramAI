package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.command.CommandProvider;
import com.example.telegrambot.constant.DialogMode;
import com.example.telegrambot.service.ChatGPTService;
import com.example.telegrambot.service.MultiSessionTelegramBot;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelegramBot extends MultiSessionTelegramBot {

    @Autowired
    private CommandProvider commandProvider;

    @Autowired
    private ChatGPTService chatGPTService;

    private DialogMode dialogMode = DialogMode.MAIN;

    public TelegramBot(@Value("${telegram.bot.name}") String name,
            @Value("${telegram.bot.token}") String token) {
        super(name, token);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        String messageText = getMessageText();
        String button = getCallbackQueryButtonKey();

        log.info("Message: {}", messageText);
        log.info("Button: {}", button);

        if ("/start".equals(messageText)) {
            commandProvider.executeCommand("/start", update);
            return;
        }

        if ("chatgpt".equals(button)) {
            dialogMode = DialogMode.GPT;
            commandProvider.executeCommand("chatgpt", update);
            return;
        }

        // Добавьте обработку других команд...

        if (dialogMode == DialogMode.GPT) {
            handleGptConversation(update.getMessage().getChatId(), messageText);
        }
    }

    private void handleGptConversation(Long chatId, String messageText) {
        sendTypingNotification(chatId);

        String answer = chatGPTService.sendMessage(loadPrompt("main"), messageText);

        sendTextMessage(answer);
    }

    // Другие необходимые методы...
}