package pl.edu.wat.wcy.view;

import javax.swing.*;

public abstract class Window extends JFrame implements IWindow {

    protected JPanel mainPanel;
    private JScrollPane pane;

    public Window() {
        super();
        mainPanel = new JPanel();
        add(mainPanel);
        setSize(320, 130);
        setVisible(true);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void addScrollPane(JScrollPane pane) {
        this.pane = pane;
        add(pane);
    }

    @Override
    public void removeMainPanel() {
        remove(mainPanel);
    }

    @Override
    public JScrollPane getScrollPane() {
        return pane;
    }

    @Override
    public boolean hasScrollPane() {
        return pane != null;
    }
}
