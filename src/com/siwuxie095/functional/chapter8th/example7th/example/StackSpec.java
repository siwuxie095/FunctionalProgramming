package com.siwuxie095.functional.chapter8th.example7th.example;

import com.siwuxie095.functional.chapter8th.example7th.Lets;

import java.util.Stack;

import static com.siwuxie095.functional.chapter8th.example7th.Lets.describe;

/**
 * 描述 Stack 的案例
 *
 * @author Jiajing Li
 * @date 2020-10-25 20:44:07
 */
@SuppressWarnings("all")
public class StackSpec {{

    describe("a stack", it -> {

        it.should("be empty when created", expect -> {
            expect.that(new Stack()).isEmpty();
        });

        it.should("push new elements onto the top of the stack", expect -> {
            Stack<Integer> stack = new Stack<>();
            stack.push(1);

            expect.that(stack.get(0)).isEqualTo(1);
        });

        it.should("pop the last element pushed onto the stack", expect -> {
            Stack<Integer> stack = new Stack<>();
            stack.push(2);
            stack.push(1);

            expect.that(stack.pop()).isEqualTo(2);
        });

    });

}}
