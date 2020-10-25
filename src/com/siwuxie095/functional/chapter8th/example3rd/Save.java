package com.siwuxie095.functional.chapter8th.example3rd;

/**
 * 保存操作代理给 Editor 方法
 *
 * @author Jiajing Li
 * @date 2020-10-25 18:19:59
 */
@SuppressWarnings("all")
public class Save implements Action {

    private final Editor editor;

    public Save(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.save();
    }

}
