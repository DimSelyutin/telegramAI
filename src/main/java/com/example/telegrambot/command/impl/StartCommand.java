package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.service.MultiSessionTelegramBot;

import java.util.List;
import java.util.Arrays;

@Component
public class StartCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override

    public void execute(Update update) {
        // Логика для команды /start
        String textMenu = telegramBot.loadMessage("mainMessage");
        List<String> buttons = Arrays.asList(
                "О боте", "about_bot",
                "ChatGPT", "chatgpt",
                "О студии", "about_studio",
                "О вас", "profile");
        telegramBot.sendTextButtonsMessage(textMenu, buttons);
    }

}