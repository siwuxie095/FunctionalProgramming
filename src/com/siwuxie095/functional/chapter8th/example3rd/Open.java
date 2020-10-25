package com.siwuxie095.functional.chapter8th.example3rd;

/**
 * 打开操作代理给 Editor 方法
 *
 * @author Jiajing Li
 * @date 2020-10-25 18:21:13
 */
@SuppressWarnings("all")
public class Open implements Action {

    private final Editor editor;

    public Open(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.open();
    }

}
