package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.context.ChatContext;

import java.util.List;

@Component
public class AboutStudioCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
        public void execute(Update update, ChatContext context) {

        String textMenu = telegramBot.loadMessage("aboutStudioMessage");

        List<String> buttons = List.of(
                "Расписание", "schedule",
                "Тренеры", "trainers",
                "Адреса", "addresses",
                "Назад (меню)", "back_to_main_menu");

        telegramBot.sendTextButtonsMessage(textMenu, buttons);
    }
    @Override
    public void processMessage(Update update, ChatContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processMessage'");
    }
}