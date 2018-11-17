package org.yk.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.junit.Test;

public class LockTest {
    
    private ReentrantLock lock = new ReentrantLock();
    private Long cnt = 0L;
    
    @Test
    public void reentrantLock_test(){
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        int testCount = 10;
        List<Integer> testList = new ArrayList<Integer>(testCount);
        for (int i = 0; i < testCount; i++){
            testList.add(i);
        }
        List<CompletableFuture<Boolean>> futureList = testList
                .stream()
                .map(x -> CompletableFuture.supplyAsync(() -> addCnt(), executorService))
                .collect(Collectors.toList());
        List<Boolean> resultList = futureList
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("exec count = "+resultList.size());
        System.out.println("cnt = " + cnt);
    }
    
    private synchronized boolean addCnt(){
        lock.lock();
        cnt++;
        System.out.println(cnt);
        lock.unlock();
        return true;
    }
}
