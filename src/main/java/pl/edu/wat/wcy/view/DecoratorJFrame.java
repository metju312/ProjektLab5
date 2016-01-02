package pl.edu.wat.wcy.view;

import javax.swing.*;

public abstract class DecoratorJFrame implements IWindow {
    protected IWindow window;
    protected JScrollPane pane;

    public DecoratorJFrame(IWindow window) {
        this.window = window;
        if (!window.hasScrollPane()) {
            generatePane();
            window.removeMainPanel();
            window.addScrollPane(pane);
        } else {
            pane = window.getScrollPane();
        }
    }

    private void generatePane(){
        pane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setViewportView(window.getMainPanel());
    }

    @Override
    public void setLocation(int x, int y) {
        window.setLocation(x, y);
    }

    @Override
    public JPanel getMainPanel() {
        return window.getMainPanel();
    }

    @Override
    public void addScrollPane(JScrollPane pane) {
        window.addScrollPane(pane);
    }

    @Override
    public void removeMainPanel() {
        window.removeMainPanel();
    }

    @Override
    public JScrollPane getScrollPane() {
        return window.getScrollPane();
    }

    @Override
    public boolean hasScrollPane() {
        return window.hasScrollPane();
    }

    @Override
    public String getPath() {
        return window.getPath();
    }
}
