package com.T82.ticket.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TimeTest {

    @Test
    void timeTest(){
        LocalDateTime parse = LocalDateTime.parse("2024-08-16T09:56:38.749487532");
        System.out.println(parse);
    }
}
