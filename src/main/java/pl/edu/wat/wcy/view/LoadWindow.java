package pl.edu.wat.wcy.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.model.entities.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadWindow extends JDialog {

    private MainWindow mainWindow;
    private JScrollPane scrollPane;

    public LoadWindow(MainWindow mainWindow) {
        super(mainWindow, "Load Window", true);
        this.mainWindow = mainWindow;
        setLoadWindowValues();
        setLoadWindowLayout();
        mainWindow.updateStateList();
        add(generateScrollPane());
        setLoadWindowVisible();
    }

    private JScrollPane generateScrollPane() {
        scrollPane = new JScrollPane(generateLoadStatesPanel());
        return scrollPane;
    }

    private JPanel generateNewStatePanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("New State");
        panel.add(button);
        return panel;
    }

    private JPanel generateLoadStatesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.add(generateNewStatePanel(), "wrap");
        for (State state : mainWindow.stateList) {
            panel.add(generatePanelForState(state), "wrap");
        }
        return panel;
    }

    private JPanel generatePanelForState(State state) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setText(state.getPath());

        JButton load = new JButton("load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        JButton delete = new JButton("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        panel.add(label);
        panel.add(load);
        panel.add(delete);
        return panel;
    }

    private void setLoadWindowLayout() {
        setLayout(new MigLayout());
    }

    private void setLoadWindowVisible() {
        setVisible(true);
    }

    private void setLoadWindowValues() {
        setLocationRelativeTo(null);
        setSize(320, 300);
    }


}
