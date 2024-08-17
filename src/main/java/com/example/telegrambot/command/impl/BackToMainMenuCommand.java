package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.context.ChatContext;

@Component
public class BackToMainMenuCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
        public void execute(Update update, ChatContext context) {

        String textMenu = telegramBot.loadMessage("mainMessage");
        List<String> buttons = List.of(
                "О боте", "about_bot",
                "ChatGPT", "chatgpt",
                "О студии", "about_studio",
                "О вас", "profile");
        telegramBot.sendTextButtonsMessage(textMenu, buttons);
    }
    @Override
    public void processMessage(Update update, ChatContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processMessage'");
    }
}
