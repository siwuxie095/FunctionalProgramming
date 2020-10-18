package com.siwuxie095.functional.chapter4th.example7th;

import org.junit.Assert;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:48:53
 */
public class Client {

    public static void main(String[] args) {
        parentDefaultUsed();
        childOverrideDefault();
        concreteBeatsDefault();
        concreteBeatsCloserDefault();
    }

    private static void parentDefaultUsed() {
        Parent parent = new ParentImpl();
        parent.welcome();
        Assert.assertEquals("Parent: Hi!", parent.getLastMessage());
    }

    private static void childOverrideDefault() {
        Child child = new ChildImpl();
        child.welcome();
        Assert.assertEquals("Child: Hi!", child.getLastMessage());
    }

    private static void concreteBeatsDefault() {
        Parent parent = new OverridingParent();
        parent.welcome();
        Assert.assertEquals("OverridingParent: Hi!", parent.getLastMessage());
    }

    private static void concreteBeatsCloserDefault() {
        Child child = new OverridingChild();
        child.welcome();
        Assert.assertEquals("OverridingParent: Hi!", child.getLastMessage());
    }

}
