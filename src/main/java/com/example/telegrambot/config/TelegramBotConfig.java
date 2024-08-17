package com.example.telegrambot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.telegrambot.TelegramBot;
import com.example.telegrambot.service.ChatGPTService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.name}")
    private String telegramBotName;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${chat.gpt.token}")
    private String chatGptToken;

    @Bean
    public TelegramBot telegramBot() {
        log.info("Start config telegram bot {}", telegramBotName);
        return new TelegramBot(telegramBotName, telegramBotToken);
    }
    @Bean
    public TelegramBotsApi telegramBotsApi() throws Exception {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public BotSession registerBot(TelegramBotsApi telegramBotsApi, TelegramBot telegramBot) throws Exception {
        log.info("Registering bot...");

        return telegramBotsApi.registerBot(telegramBot);
    }

    @Bean
    public ChatGPTService chatGPTService() {
        return new ChatGPTService(chatGptToken);
    }
}