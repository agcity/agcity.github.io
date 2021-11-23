package io.github.agcity.concurrent;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@SpringBootTest
class ConcurrentApplicationTests {

    @Test
    public void concurrentTest() throws InterruptedException {
        final int clientThread = 5000;
        final int semThread = 100;
        AtomicInteger count = new AtomicInteger();
        Semaphore semaphore = new Semaphore(semThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(clientThread);
        for (int i = 0; i < clientThread; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    count.getAndIncrement();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        executorService.shutdown();
        log.info("计数结果： count = {}", count.get());
    }
}
