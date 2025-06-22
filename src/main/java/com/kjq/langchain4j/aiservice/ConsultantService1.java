package com.kjq.langchain4j.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel"
)
public interface ConsultantService1 {
    //用于聊天方法
    public String chat(String message);
}
