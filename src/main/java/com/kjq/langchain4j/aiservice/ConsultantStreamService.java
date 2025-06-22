package com.kjq.langchain4j.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        // chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface ConsultantStreamService {
    //用于聊天方法
    @SystemMessage("你是一个医疗只能助手，能帮助用户解决各种疾病问题。")
    public Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
