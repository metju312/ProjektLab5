package pl.edu.wat.wcy.view;

import javax.swing.*;

public class CheckBoxPanel extends JPanel {
    public JCheckBox horizontalBar;
    public JCheckBox verticalBar;

    public CheckBoxPanel() {
        super();
        setCheckBoxPanelLayout();
        add(generateCheckBoxes());
    }

    private void setCheckBoxPanelLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Select The Bar"));
    }

    private JPanel generateCheckBoxes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(generateUpPanel());
        panel.add(generateDownPanel());

        return panel;
    }

    private JPanel generateDownPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        verticalBar = new JCheckBox();
        panel.add(verticalBar);
        panel.add(new JLabel("Vertical Bar"));
        return panel;
    }

    private JPanel generateUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        horizontalBar = new JCheckBox();
        panel.add(horizontalBar);
        panel.add(new JLabel("Horizontal Bar"));
        return panel;
    }
}
