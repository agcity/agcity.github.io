package com.agcity.swan.cglib.demo;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("-------before---");
        Object result = proxy.invokeSuper(obj,args);
        System.out.println("--------after---");
        return result;
    }


}
