package com.deepchecks;

import com.deepchecks.client.QuickstartApp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("QuickstartApp started...");
        new QuickstartApp().start();
        log.info("QuickstartApp finished...");
    }
}