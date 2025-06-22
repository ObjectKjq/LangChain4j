package com.kjq.langchain4j.memory;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kjq.langchain4j.model.entity.ChatMemory;
import com.kjq.langchain4j.service.ChatMemoryService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 自定义基于mysql数据库的持久化记忆
 */
@Component
public class JdbcChatMemoryStore implements ChatMemoryStore {

    @Resource
    private ChatMemoryService chatMemoryService;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        LambdaQueryWrapper<ChatMemory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMemory::getMemoryId, memoryId);
        ChatMemory chatMemory = chatMemoryService.getOne(wrapper);
        if (ObjectUtils.isEmpty(chatMemory)) {
            return List.of();
        }
        try {
            return JSONUtil.toList(chatMemory.getMessages(), ChatMessage.class);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        LambdaQueryWrapper<ChatMemory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMemory::getMemoryId, memoryId);
        ChatMemory chatMemory = chatMemoryService.getOne(wrapper);
        if (ObjectUtils.isNotEmpty(chatMemory)){
            chatMemory.setMessages(JSONUtil.toJsonStr(list));
            chatMemoryService.updateById(chatMemory);
        } else {
            ChatMemory chatMemoryDo = new ChatMemory();
            chatMemoryDo.setMemoryId(memoryId.toString());
            chatMemoryDo.setMessages(JSONUtil.toJsonStr(list));
            chatMemoryDo.setCreator("1");
            chatMemoryDo.setCreateTime(new Date());
            chatMemoryDo.setUpdater("1");
            chatMemoryDo.setUpdateTime(new Date());
            chatMemoryService.save(chatMemoryDo);
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        LambdaQueryWrapper<ChatMemory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMemory::getMemoryId, memoryId);
        chatMemoryService.remove(wrapper);
    }
}
