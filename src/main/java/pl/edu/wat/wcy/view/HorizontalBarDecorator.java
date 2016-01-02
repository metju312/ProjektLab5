package pl.edu.wat.wcy.view;

import javax.swing.*;

public class HorizontalBarDecorator extends DecoratorJFrame {
    public HorizontalBarDecorator(IWindow window) {
        super(window);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
