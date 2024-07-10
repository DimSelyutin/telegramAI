package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.service.ChatGPTService;
import com.example.telegrambot.service.MultiSessionTelegramBot;

@Component
public class TelegramBot extends MultiSessionTelegramBot {

    @Autowired
    private ChatGPTService chatGPTService;

    public TelegramBot(String name, String token) {
        super(name, token);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        String message = getMessageText();
        String answer = chatGPTService.sendMessage("Ответь на вопрос:", message);
        sendTextMessage(answer);
    }
}
