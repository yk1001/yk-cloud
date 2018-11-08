package org.yk.demo.mgmt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yk.demo.mgmt.AbstractTest;

public class RedisClientTest extends AbstractTest{

    private int SUCCESS_CNT;
    private int FAILED_CNT;
    private int ERROR_CNT;
    @Autowired
    private RedisClient redisClient;

    // @Test
    public void setStr_test(){
        redisClient.setStr("key", "value", 5000L);
    }
    
   // @Test
    public void addLock_test(){
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        int testCount = 10;
        List<Integer> testList = new ArrayList<Integer>(testCount);
        for (int i = 0; i < testCount; i++){
            testList.add(i);
        }
        List<CompletableFuture<Boolean>> futureList = testList
                 .stream()
                 .map(x -> CompletableFuture.supplyAsync(() -> {
                     String lockKey = "addLock_test";
                     try {
                         if(redisClient.addLock(lockKey, 100L, 10)){
                             SUCCESS_CNT++;
                             System.out.println(SUCCESS_CNT);
                         }
                    } finally {
                        redisClient.deleteRedisLock(lockKey);
                    }
                   return true;
                 }, executorService))
                 .collect(Collectors.toList());
         List<Boolean> resultList = futureList
                 .stream()
                 .map(CompletableFuture::join)
                 .collect(Collectors.toList());
         System.out.println("exec count = "+resultList.size());
         System.out.println("SUCCESS_CNT = " + SUCCESS_CNT);
    }
    
}
