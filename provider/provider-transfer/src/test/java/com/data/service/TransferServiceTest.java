package com.data.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferServiceTest {

    @Autowired
    TransferService transferService;

    @Test
    public void transfer() {
        transferService.transfer(6);

    }
}
