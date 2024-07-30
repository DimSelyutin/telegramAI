package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.telegrambot.constant.DialogMode;
import com.example.telegrambot.entity.TelegramUser;
import com.example.telegrambot.service.ChatGPTService;
import com.example.telegrambot.service.MultiSessionTelegramBot;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TelegramBot extends MultiSessionTelegramBot {
    @Autowired
    private ChatGPTService chatGPTService;

    private DialogMode dialogMode = DialogMode.MAIN;

    public TelegramBot(String name, String token) {
        super(name, token);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        Long chatId = getCurrentChatId();
        String messageText = getMessageText();
        String button = getCallbackQueryButtonKey();
        log.info(messageText);
        if (messageText.equals("/start")) {
            dialogMode = DialogMode.MAIN;
            List<String> buttons = new ArrayList<>();
            buttons.add("О боте");
            buttons.add("about_bot");
            buttons.add("ChatGPT");
            buttons.add("chatgpt");
            buttons.add("О студии");
            buttons.add("about_studio");
            buttons.add("О вас");
            buttons.add("profile");
            // ф-я считывания из файла
            String textMenu = loadMessage("main");
            sendTextButtonsMessage(textMenu, buttons);

            return;
        }
        if (messageText.equals("/gpt")) {
            dialogMode = DialogMode.GPT;

            sendTextButtonsMessage("Задайте ваш вопрос");
            return;
        }
        if (dialogMode == dialogMode.MAIN || button.equals("chatgpt")) {
            if (button.equals("profile")) {
                Long userId = getUserId();
                String firstname = getUserFirstName();
                String lastname = getUserLastName();
                String phone = getUserPhone();
                TelegramUser tgUser = new TelegramUser();
                tgUser.setFirstName(firstname);
                tgUser.setId(userId);
                tgUser.setLastName(lastname);
                tgUser.setPhone(phone);
                log.info("User:", tgUser.toString());
                sendTextMessage("Здравствуйте " + firstname);
                return;
            }
        }

        if (dialogMode == dialogMode.GPT) {
            sendTypingNotification(chatId);
            log.info("messages: {}", chatGPTService.getMessageHistory());
            String answer = chatGPTService.sendMessage(loadPrompt("posts"), messageText);

            sendTextMessage(answer);
        }

    }
}
