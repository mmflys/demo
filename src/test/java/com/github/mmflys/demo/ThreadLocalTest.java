package com.github.mmflys.demo;

public class ThreadLocalTest {

    static ThreadLocal<byte[]> tl = null;

    public static void main(String[] args) throws InterruptedException {
        testWeakReference();
    }

    private static void testStrongReference() throws InterruptedException {
        System.gc();
        new Thread(() -> {
            tl = ThreadLocal.withInitial(() -> new byte[1024]);
            tl.set(new byte[1024 * 1024 * 10]);
            System.gc();
            Thread thread=Thread.currentThread();
            System.out.println(tl.get().length/1024);
            while (true) {

            }
        }).start();
        Thread.sleep(3000);
        System.gc();
        System.out.println("ThreadLocal: " + tl);
        System.out.println(tl.get().length/1024);
    }

    public static void testWeakReference(){
        System.gc();
        new Thread(() -> {
            ThreadLocal.withInitial(() -> new byte[1024]).set(new byte[1024 * 1024 * 10]);
            System.gc();
            Thread thread=Thread.currentThread();
            while (true) {

            }
        }).start();
        System.gc();
    }

}
