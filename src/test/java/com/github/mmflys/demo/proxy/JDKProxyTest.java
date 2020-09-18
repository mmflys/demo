package com.github.mmflys.demo.proxy;

import org.testng.annotations.Test;

import java.lang.reflect.Proxy;

public class JDKProxyTest {

    @Test
    public void proxyTest() {
        Man man = new Man();
        Human proxyHuman = (Human) Proxy.newProxyInstance(man.getClass().getClassLoader(),
                man.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("Pre enhance...");
                    Object result = method.invoke(man, args);
                    System.out.println("Post enhance...");
                    return result;
                });
        proxyHuman.speak();
    }

}
