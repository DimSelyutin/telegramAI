package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;

@Component
public class ScheduleCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update) {
        String textMenu = telegramBot.loadMessage("scheduleMessage");
        telegramBot.sendTextButtonsMessage(textMenu);
    }
}