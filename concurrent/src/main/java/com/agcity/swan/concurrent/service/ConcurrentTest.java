package com.agcity.swan.concurrent.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ConcurrentTest {

  private static final int clientTotal = 5000; //总请求数
  private static final int threadTotal = 200;  //并发数
  private static final AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch cdl = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      executorService.execute(
          () -> {
            try {
              semaphore.acquire();
              countIncrement();
              semaphore.release();
            } catch (Exception e) {
              e.printStackTrace();
            }
            cdl.countDown();
          });
    }
    cdl.await();
    executorService.shutdown();
    log.info("执行的总数: count = " + count.intValue());
  }

  private static void countIncrement() {
    count.getAndIncrement();
  }
}
