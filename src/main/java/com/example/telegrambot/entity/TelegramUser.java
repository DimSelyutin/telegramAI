package com.example.telegrambot.entity;

import java.time.LocalDateTime;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;

    private LocalDateTime creationDate = LocalDateTimeUtil.now();


}
