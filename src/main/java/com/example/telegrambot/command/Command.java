package com.example.telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.context.ChatContext;

public interface Command {
    void execute(Update update, ChatContext context);
    void processMessage(Update update, ChatContext context);
}
