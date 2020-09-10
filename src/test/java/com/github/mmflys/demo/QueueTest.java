package com.github.mmflys.demo;

import org.testng.annotations.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Test
public class QueueTest {
    public static final int NUMBERS = 1000000;

    @Test
    public void arrayQueueTest() {
        long insertStartTime = System.currentTimeMillis();
        Queue<Integer> queue = new ArrayBlockingQueue<Integer>(NUMBERS);
        for (int i = 0; i < NUMBERS; i++) {
            queue.add(i);
        }
        long insertEndTime = System.currentTimeMillis();
        System.out.println("ArrayBlockingQueue spend " + (insertEndTime - insertStartTime) + "ms to insert " + NUMBERS / 10000 + "w numbers");
        long getStartTime = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            int n = queue.poll();
        }
        long getEndTime = System.currentTimeMillis();
        System.out.println("ArrayBlockingQueue spend " + (getEndTime - getStartTime) + "ms to traverse " + NUMBERS / 10000 + "w numbers");
    }

    @Test
    public void linkedQueueTest() {
        long insertStartTime = System.currentTimeMillis();
        Queue<Integer> queue = new LinkedBlockingQueue<>(NUMBERS);
        for (int i = 0; i < NUMBERS; i++) {
            queue.add(i);
        }
        long insertEndTime = System.currentTimeMillis();
        System.out.println("LinkedBlockingQueue spend " + (insertEndTime - insertStartTime) + "ms to insert " + NUMBERS / 10000 + "w numbers");
        long getStartTime = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            int n = queue.poll();
        }
        long getEndTime = System.currentTimeMillis();
        System.out.println("LinkedBlockingQueue spend " + (getEndTime - getStartTime) + "ms to traverse " + NUMBERS / 10000 + "w numbers");
    }

    @Test
    public void paddingArrayQueueTest() {
        long insertStartTime = System.currentTimeMillis();
        Queue<RhsPaddingBase> queue = new ArrayBlockingQueue<RhsPaddingBase>(NUMBERS);
        for (int i = 0; i < NUMBERS; i++) {
            queue.add(new RhsPaddingBase(i));
        }
        long insertEndTime = System.currentTimeMillis();
        System.out.println("ArrayBlockingQueue spend " + (insertEndTime - insertStartTime) + "ms to insert " + NUMBERS / 10000 + "w PaddingInteger");
        long getStartTime = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            long value = queue.poll().getValue();
        }
        long getEndTime = System.currentTimeMillis();
        System.out.println("ArrayBlockingQueue spend " + (getEndTime - getStartTime) + "ms to traverse " + NUMBERS / 10000 + "w PaddingInteger");
    }

    public class PaddingInteger extends LhsPadingBase {
        private long value;

        public PaddingInteger(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    public class LhsPadingBase {
        long[] lhsArray = new long[63];
    }

    public class RhsPaddingBase extends PaddingInteger {
        long[] rhsArray = new long[63];

        public RhsPaddingBase(long value) {
            super(value);
        }
    }
}
