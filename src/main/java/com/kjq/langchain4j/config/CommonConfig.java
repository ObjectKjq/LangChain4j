package com.kjq.langchain4j.config;

import com.kjq.langchain4j.aiservice.ConsultantService;
import com.kjq.langchain4j.memory.JdbcChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Resource
    private OpenAiChatModel model;
    @Resource
    private ChatMemoryStore jdbcChatMemoryStore;

    @Bean
    public ConsultantService consultantService() {
        ConsultantService consultantService = AiServices.builder(ConsultantService.class)
                .chatModel(model)
                .build();
        return consultantService;
    }

    /**
     * 创建基于内存的公共会话记忆
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(4)
                .build();
    }

    /**
     * 会话记忆对象提供者
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(4)
                        .chatMemoryStore(jdbcChatMemoryStore)
                        .build();
            }
        };
    }

}
