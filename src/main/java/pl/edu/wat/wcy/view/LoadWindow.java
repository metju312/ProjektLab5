package pl.edu.wat.wcy.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.model.dao.StateDao;
import pl.edu.wat.wcy.model.entities.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadWindow extends JDialog {

    private MainWindow mainWindow;
    private JScrollPane scrollPane;
    private JPanel listPanel;

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
        listPanel = new JPanel();
        listPanel.setLayout(new MigLayout());
        listPanel.add(generateNewStatePanel(), "wrap");
        for (State state : mainWindow.stateList) {
            listPanel.add(generatePanelForState(state), "wrap");
        }
        return listPanel;
    }

    private JPanel generatePanelForState(State state) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setText(state.getDate());

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
                StateDao stateDao = new StateDao();
                stateDao.delete(state.getId());
                mainWindow.updateStateList();
                revalidateLoadWindow();
            }
        });


        panel.add(label);
        panel.add(load);
        panel.add(delete);
        return panel;
    }

    private void revalidateLoadWindow() {
        //this.removeAll();
        //add(generateScrollPane());

        //scrollPane.removeAll();
        scrollPane.remove(listPanel);
        scrollPane.add(generateLoadStatesPanel());
        scrollPane.getViewport().revalidate();
//        revalidate();
//        repaint();

    }

    private void setLoadWindowLayout() {
        setLayout(new MigLayout());
    }

    private void setLoadWindowVisible() {
        setVisible(true);
    }

    private void setLoadWindowValues() {
        //setLocationRelativeTo(null);
        setSize(340, 400);
    }


}
