package com.example.telegrambot;

import com.example.telegrambot.command.CommandProvider;
import com.example.telegrambot.constant.DialogMode;
import com.example.telegrambot.context.ChatContext;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.service.MultiSessionTelegramBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelegramBot extends MultiSessionTelegramBot {

    @Autowired
    private CommandProvider commandProvider;

    // Карта для хранения контекста каждого чата по chatId
    private final Map<Long, ChatContext> chatContexts = new HashMap<>();

    @Autowired
    public TelegramBot(String name, String token) {
        super(name, token);

    }

    @Override
    public void onUpdateEventReceived(Update update) {
        Long chatId = getCurrentChatId();
        String messageText = getMessageText();

        // Получаем или создаем контекст для текущего пользователя (чата)
        ChatContext context = chatContexts.computeIfAbsent(chatId, k -> new ChatContext());
        

        if (messageText != null && !messageText.isEmpty()) {
            log.info("Message: {}", messageText);

            try {
                // Обрабатываем сообщения в режиме GPT.
                if (context.getDialogMode() == DialogMode.GPT) {
                    commandProvider.processMessage(update, context);
                }
                // Выполняем команды по тексту сообщения.
                commandProvider.executeCommand(messageText, update, context);

            } catch (IllegalArgumentException e) {
                log.error("Error executing command: ", e);
                sendTextMessage("Unknown command: " + messageText);
            }
        }

        // Также обработайте кнопки из callbackQuery.
        String button = getCallbackQueryButtonKey();
        if (button != null && !button.isEmpty() ) {
            log.info("MessageButton: {}", button);
            try {
                commandProvider.executeCommand(button, update, context);
            } catch (IllegalArgumentException e) {
                log.error("Error executing button action: ", e);
                sendTextMessage("Unknown button action: " + button);
            }
        }
    }
}