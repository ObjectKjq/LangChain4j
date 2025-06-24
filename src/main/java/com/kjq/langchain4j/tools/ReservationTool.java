package com.kjq.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationTool {

    public List<String> names = new ArrayList<>();

    @Tool(value = "添加一个看病者的预约")
    public void addReservation(@P(value = "姓名") String name) {
        names.add(name);
    }

    @Tool("查看所有看病的人")
    public List<String> getAllReservation() {
        return names;
    }

}
