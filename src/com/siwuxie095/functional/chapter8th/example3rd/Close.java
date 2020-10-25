package com.siwuxie095.functional.chapter8th.example3rd;

/**
 * 关闭操作代理给 Editor 方法
 *
 * @author Jiajing Li
 * @date 2020-10-25 18:22:21
 */
@SuppressWarnings("all")
public class Close implements Action {

    private final Editor editor;

    public Close(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.close();
    }

}
