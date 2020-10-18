package com.siwuxie095.functional.chapter4th.example7th;

/**
 * @author Jiajing Li
 * @date 2020-10-18 13:54:44
 */
@SuppressWarnings("all")
public class OverridingParent extends ParentImpl {

    @Override
    public void welcome() {
        message("OverridingParent: Hi!");
    }
}
