package com.siwuxie095.functional.chapter8th.example3rd;

import org.junit.Test;

/**
 * @author Jiajing Li
 * @date 2020-10-25 18:31:55
 */
@SuppressWarnings("all")
public class Client {

    @Test
    public void classBasedCommand() {
        MockEditor editor = new MockEditor();

        Macro macro = new Macro();
        macro.record(new Open(editor));
        macro.record(new Save(editor));
        macro.record(new Close(editor));
        macro.run();

        editor.check();
    }


    @Test
    public void lambdaBasedCommand() {
        MockEditor editor = new MockEditor();

        Macro macro = new Macro();
        macro.record(() -> editor.open());
        macro.record(() -> editor.save());
        macro.record(() -> editor.close());
        macro.run();

        editor.check();
    }


    @Test
    public void referenceBasedCommand() {
        MockEditor editor = new MockEditor();

        Macro macro = new Macro();
        macro.record(editor::open);
        macro.record(editor::save);
        macro.record(editor::close);
        macro.run();

        editor.check();
    }

}
