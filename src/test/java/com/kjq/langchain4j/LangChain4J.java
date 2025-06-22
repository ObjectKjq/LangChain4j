package com.kjq.langchain4j;

import com.kjq.langchain4j.config.ApiKeyConfig;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.Test;

public class LangChain4J {

    @Test
    public void test01() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeyConfig.apiKey)
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("Say 'Hello World'");
        System.out.println(answer); // Hello World
    }
}
