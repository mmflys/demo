package com.github.mmflys.demo.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class CGLIBProxyTest {

    @Test
    public void proxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Man.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                    System.out.println("Pre enhance...");
                    Object object = proxy.invokeSuper(obj, args);
                    System.out.println("Post enhance...");
                    return object;
                }
        );
        Man proxyMan = (Man) enhancer.create();
        proxyMan.speak();
    }

}
