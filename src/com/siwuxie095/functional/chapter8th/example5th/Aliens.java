package com.siwuxie095.functional.chapter8th.example5th;

/**
 * 外星人观察到人类登陆月球
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:34:17
 */
@SuppressWarnings("all")
public class Aliens implements LandingObserver {

    @Override
    public void observeLanding(String name) {
        if (name.contains("Apollo")) {
            System.out.println("They're distracted, lets invade earth!");
        }
    }

}
