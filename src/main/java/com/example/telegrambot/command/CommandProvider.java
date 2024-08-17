package com.example.telegrambot.command;

import com.example.telegrambot.command.impl.*;
import com.example.telegrambot.context.ChatContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandProvider {

    private final Map<String, Command> commandMap = new HashMap<>();

    @Autowired
    public CommandProvider(StartCommand startCommand,
            AboutBotCommand aboutBotCommand,
            @Lazy ChatGptCommand chatGptCommand,
            AboutStudioCommand aboutStudioCommand,
            ProfileCommand profileCommand,
            ScheduleCommand scheduleCommand,
            TrainersCommand trainersCommand,
            AddressesCommand addressesCommand,
            BackToMainMenuCommand backToMainMenuCommand) {
        commandMap.put("/start", startCommand);
        commandMap.put("about_bot", aboutBotCommand);
        commandMap.put("chatgpt", chatGptCommand);
        commandMap.put("about_studio", aboutStudioCommand);
        commandMap.put("profile", profileCommand);
        commandMap.put("schedule", scheduleCommand);
        commandMap.put("trainers", trainersCommand);
        commandMap.put("addresses", addressesCommand);
        commandMap.put("back_to_main_menu", backToMainMenuCommand);
        // Добавьте другие команды сюда при необходимости.
    }

    public void executeCommand(String commandName, Update update, ChatContext context) {
        Command command = commandMap.get(commandName);
        if (command != null) {
            command.execute(update, context);
            
        } else {
            // Обработка неизвестной команды.
            throw new IllegalArgumentException("Unknown command: " + commandName);
        }
    }

    public void processMessage(Update update, ChatContext context) {
        for (Command command : commandMap.values()) {
            if (command instanceof ChatGptCommand) {
                command.processMessage(update, context);
            }
        }
    }
}