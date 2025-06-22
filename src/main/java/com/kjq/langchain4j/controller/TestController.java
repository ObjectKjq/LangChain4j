package com.kjq.langchain4j.controller;

import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private OpenAiChatModel model;

    @GetMapping("/chat")
    public String chat(String message) {
        return model.chat(message);
    }
}
