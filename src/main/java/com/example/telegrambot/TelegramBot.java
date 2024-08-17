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
    public TelegramBot(String name, String token) {
        super(name, token);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        String messageText = getMessageText();

        if (messageText != "") {
            log.info("Message: {}", messageText);
            try {
                commandProvider.executeCommand(messageText, update);
            } catch (IllegalArgumentException e) {
                log.error("Error executing command: ", e);
                sendTextMessage("Unknown command: " + messageText);
            }
        }

        // Также обработайте кнопки из callbackQuery.
        String button = getCallbackQueryButtonKey();
        if (button != "") {
            log.info("MessageButton: {}", button);
            try {
                commandProvider.executeCommand(button, update);
            } catch (IllegalArgumentException e) {
                log.error("Error executing button action: ", e);
                sendTextMessage("Unknown button action: " + button);
            }
        }

    }
}