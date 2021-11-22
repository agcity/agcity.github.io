package com.agcity.swan.cglib.demo;

import net.sf.cglib.proxy.Enhancer;

public class MainTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibDemo.class);
        enhancer.setCallback(new CGlibProxy());
        CglibDemo demo = (CglibDemo) enhancer.create();
        demo.test();
    }
}
