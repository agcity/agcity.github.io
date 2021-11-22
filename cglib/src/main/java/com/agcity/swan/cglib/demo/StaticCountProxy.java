package com.agcity.swan.cglib.demo;

public class StaticCountProxy implements Count {

    private Count count;

    public StaticCountProxy(Count count){
        this.count = count;
    }

    @Override
    public void queryCount() {
        System.out.println("---before count---");

        count.queryCount();

        System.out.println("---after count---");

    }
}
