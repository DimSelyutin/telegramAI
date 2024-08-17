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
public class AboutBotCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update, ChatContext context) {
        List<String> buttons = List.of("Назад", "back_to_main_menu");
        String textMenu = telegramBot.loadMessage("aboutBotMessage");
        telegramBot.sendTextButtonsMessage(textMenu, buttons);

    }
    @Override
    public void processMessage(Update update, ChatContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processMessage'");
    }
}