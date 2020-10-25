package com.siwuxie095.functional.chapter8th.example5th;

import java.util.ArrayList;
import java.util.List;

/**
 * Moon 类，当然不如现实世界中那么完美
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:32:45
 */
@SuppressWarnings("all")
public class Moon {

    private final List<LandingObserver> observers = new ArrayList<>();

    public void land(String name) {
        for (LandingObserver observer : observers) {
            observer.observeLanding(name);
        }
    }

    public void startSpying(LandingObserver observer) {
        observers.add(observer);
    }

}
