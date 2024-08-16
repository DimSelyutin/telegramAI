package com.example.telegrambot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.telegrambot.command.impl.ChatGptCommand;
import com.example.telegrambot.command.impl.StartCommand;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Component
public class CommandProvider {
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandProvider(List<Command> commands) {
        for (Command command : commands) {
            if (command instanceof StartCommand) {
                commandMap.put("/start", command);
            } else if (command instanceof ChatGptCommand) {
                commandMap.put("chatgpt", command);
            }
            // Добавьте другие команды в map...
        }
    }

    public void executeCommand(String commandKey, Update update) {
        Command command = commandMap.get(commandKey);
        if (command != null) {
            command.execute(update);
        } else {
            log.warn("Unknown command: {}", commandKey);
        }
    }
}
