package pl.edu.wat.wcy.view;

import javax.swing.*;

public interface IWindow {
    void setLocation(int x, int y);

    JPanel getMainPanel();

    void addScrollPane(JScrollPane pane);

    void removeMainPanel();

    JScrollPane getScrollPane();

    boolean hasScrollPane();

    String getPath();

    void disposeMe();
}
