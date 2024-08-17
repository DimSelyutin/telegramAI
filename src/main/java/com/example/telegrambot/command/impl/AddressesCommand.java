package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import java.util.List;

@Component
public class AddressesCommand implements Command {

    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update) {
        List<String> buttons = List.of("Назад", "back");
                String textMenu = telegramBot.loadMessage("addressesMessage");
                telegramBot.sendTextButtonsMessage(textMenu, buttons);
    }
}
