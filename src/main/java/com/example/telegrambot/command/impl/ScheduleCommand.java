package com.example.telegrambot.command.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.context.ChatContext;

@Component
public class ScheduleCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update, ChatContext context) {

        String textMenu = telegramBot.loadMessage("scheduleMessage");
        List<String> buttons = List.of("Назад", "back_to_main_menu");
        telegramBot.sendTextButtonsMessage(textMenu, buttons);
    }

    @Override
    public void processMessage(Update update, ChatContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processMessage'");
    }
}