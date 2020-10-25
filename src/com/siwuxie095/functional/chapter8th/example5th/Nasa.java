package com.siwuxie095.functional.chapter8th.example5th;

/**
 * NASA 也能观察到有人登陆月球
 *
 * @author Jiajing Li
 * @date 2020-10-25 19:34:50
 */
@SuppressWarnings("all")
public class Nasa implements LandingObserver {

    @Override
    public void observeLanding(String name) {
        if (name.contains("Apollo")) {
            System.out.println("We made it!");
        }
    }

}
