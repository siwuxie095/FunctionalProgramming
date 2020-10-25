package com.siwuxie095.functional.chapter8th.example3rd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiajing Li
 * @date 2020-10-25 18:23:22
 */
@SuppressWarnings("all")
public class Macro {

    private final List<Action> actions;

    public Macro() {
        actions = new ArrayList<>();
    }

    public void record(Action action) {
        actions.add(action);
    }

    public void run() {
        actions.forEach(Action::perform);
    }

}
