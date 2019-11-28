package com.akali.message.producer.service;


import com.akali.message.producer.api.ProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerServiceImplTest {
    @Autowired
    ProducerService producerService;

    @Test
    public void publishGoods() {
        producerService.publishGoods(1L);
    }
}
