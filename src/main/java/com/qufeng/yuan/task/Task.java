package com.qufeng.yuan.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

	@Scheduled(fixedDelay = 5000)  
    void doSomethingWithDelay(){  
        System.out.println("I'm doing with delay now!");  
    }
	
	@Scheduled(fixedRate = 5000)  
    void doSomethingWithRate(){  
        System.out.println("I'm doing with rate now!");  
    }  
      
    @Scheduled(cron = "0/5 * * * * *")  
    void doSomethingWith(){  
        System.out.println("I'm doing with cron now!");  
    }
}
