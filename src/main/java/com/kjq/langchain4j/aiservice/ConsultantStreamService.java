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
        streamingChatModel = "openAiStreamingChatModel", // 使用自定义的流式聊天模型
        // chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider", // 使用自定义的内存提供者
        contentRetriever = "contentRetriever" // 使用自定义的内容检索器
)
public interface ConsultantStreamService {
    //用于聊天方法
    @SystemMessage("你是一个医疗只能助手，能帮助用户解决各种疾病问题。")
    public Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
