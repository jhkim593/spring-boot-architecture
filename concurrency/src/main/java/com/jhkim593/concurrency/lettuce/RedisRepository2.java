//package com.jhkim593.concurrency.lettuce;
//
//import org.redisson.Redisson;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.awt.print.Book;
//import java.util.concurrent.TimeUnit;
//
//@Transactional
//public void purchase(final Long bookId, final long quantity) {
//    new Redisson();
//    RLock lock = redissonClient.getLock(String.format("purchase:book:%d", bookId));
//    try {
//        boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
//        if (!available) {
//            System.out.println("redisson getLock timeout");
//            return;
//        }
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(IllegalArgumentException::new);
//        book.purchase(quantity);
//    } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//    } finally {
//        lock.unlock();
//    }
//}