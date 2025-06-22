package com.kjq.langchain4j.controller;

import com.kjq.langchain4j.aiservice.ConsultantService;
import com.kjq.langchain4j.aiservice.ConsultantService1;
import com.kjq.langchain4j.aiservice.ConsultantStreamService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private OpenAiChatModel model;
    @Resource
    private ConsultantService consultantService;
    @Resource
    private ConsultantService1 consultantService1;
    @Resource
    private ConsultantStreamService consultantStreamService;

    @GetMapping("/chat")
    public String chat(String message) {
        return model.chat(message);
    }

    @GetMapping("/consultant")
    public String consultant(String message) {
        return consultantService1.chat(message);
    }

    @GetMapping(value = "/steam", produces = "text/event-stream")
    public Flux<String> steam(String memoryId, String message) {
        return consultantStreamService.chat(memoryId, message);
    }
}
