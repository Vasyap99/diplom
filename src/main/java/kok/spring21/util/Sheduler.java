package kok.spring21.util;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import kok.spring21.models.Task;
import kok.spring21.dto.TaskDto;
import java.util.List;

import kok.spring21.TaskService;

//RabbitMQ:
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

@Component
public class Sheduler {
    @Autowired TaskService ts;

    @Scheduled(fixedDelay = 24L*3600L*1000L)
    public synchronized void scheduleFixedDelayTask() {
        System.out.println("-----Fixed delay task - " + System.currentTimeMillis() / 1000);
        List<TaskDto> l=ts.showDayBeforeDeadline();
        for(var i:l){
            System.out.println("--found dl task");
	try{			                   
            String QUEUE="TASK_USER_QUEUE_"+i.getExecutor();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE, false, false, false, null);
                String message = "У Вас имеются задачи с истекающим дедлайном!";
                channel.basicPublish("", QUEUE, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }catch(Exception e){
            System.out.println(">>RabbitMQ error!!!");
        }

        }
    }

}