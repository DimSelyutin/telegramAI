package com.example.telegrambot.context;


import com.example.telegrambot.constant.DialogMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatContext {
    private DialogMode dialogMode = DialogMode.MAIN;
    // Дополнительные поля контекста, если необходимо
}