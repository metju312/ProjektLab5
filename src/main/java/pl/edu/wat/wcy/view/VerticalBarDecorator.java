package pl.edu.wat.wcy.view;

import javax.swing.*;

public class VerticalBarDecorator extends DecoratorJFrame {
    public VerticalBarDecorator(IWindow window) {
        super(window);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
}
