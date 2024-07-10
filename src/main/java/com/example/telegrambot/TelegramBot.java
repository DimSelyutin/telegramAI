package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.telegrambot.constant.DialogMode;
import com.example.telegrambot.entity.TelegramUser;
import com.example.telegrambot.service.ChatGPTService;
import com.example.telegrambot.service.MultiSessionTelegramBot;
import java.util.*;
import cn.hutool.system.UserInfo;
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
            log.info("Equals", dialogMode == dialogMode.GPT);
            log.info("Equals", dialogMode.equals(dialogMode.GPT));
            log.info("Equals", dialogMode == dialogMode.GPT);
            String answer = chatGPTService.sendMessage("Ответь на вопрос:", messageText);
            sendTextMessage(answer);
        }

    }
}
