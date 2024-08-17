package com.example.telegrambot.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.constant.DialogMode;
import com.example.telegrambot.context.ChatContext;
import com.example.telegrambot.service.ChatGPTService;

@Component
public class ChatGptCommand implements Command {

    @Autowired
    private ChatGPTService chatGPTService;
    
    @Autowired
    @Lazy
    private TelegramBot telegramBot;

    @Override
    public void execute(Update update, ChatContext context) {
        context.setDialogMode(DialogMode.GPT);
        telegramBot.sendTextButtonsMessage("Задайте ваш вопрос");
    }

    @Override
    public void processMessage(Update update, ChatContext context) {
        if (context.getDialogMode() == DialogMode.GPT) {
            Long chatId = telegramBot.getCurrentChatId();
            String messageText = telegramBot.getMessageText();

            telegramBot.sendTypingNotification(chatId);
            String answer = chatGPTService.sendMessage(telegramBot.loadPrompt("main"), messageText);

            telegramBot.sendTextMessage(answer);
        }
    }

}