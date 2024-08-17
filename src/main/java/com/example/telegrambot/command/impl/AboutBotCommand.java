package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;

@Component
public class AboutBotCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update) {
        List<String> buttons = List.of("Назад", "back_to_main_menu");
        String textMenu = telegramBot.loadMessage("aboutBotMessage");
        telegramBot.sendTextButtonsMessage(textMenu, buttons);

    }
}