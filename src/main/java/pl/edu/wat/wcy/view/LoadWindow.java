package pl.edu.wat.wcy.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.model.dao.CheckboxDao;
import pl.edu.wat.wcy.model.dao.StateDao;
import pl.edu.wat.wcy.model.entities.Checkbox;
import pl.edu.wat.wcy.model.entities.State;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

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
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.resetMainWindow();
                closeLoadWindow();
            }
        });
        panel.add(button);
        return panel;
    }

    private void closeLoadWindow() {
        this.dispose();
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
                loadState(state);
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

    private void loadState(State state) {
        mainWindow.resetMainWindow();
        mainWindow.setBrowseWindowList();
        List<Checkbox> checkBoxList = generateNewCheckBoxListBasedOnState(state);
        mainWindow.textField.setText("" + checkBoxList.size());
        mainWindow.generateNCheckBoxPanels(checkBoxList.size());
        mainWindow.setCheckBoxesValues(checkBoxList);
        try {
            mainWindow.repaintMosaic(state.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainWindow.generateNBrowseWindows(checkBoxList.size());
        mainWindow.setBrowseWindowsPaths(checkBoxList);
        mainWindow.saveButton.setEnabled(true);
        mainWindow.textField.setEnabled(false);
        mainWindow.generateCheckBoxesButton.setEnabled(false);
        mainWindow.generateBrowseWindowsButton.setEnabled(false);
        for (CheckBoxPanel panel : mainWindow.checkBoxPanelList) {
            panel.horizontalBar.setEnabled(false);
            panel.verticalBar.setEnabled(false);
        }
        closeLoadWindow();
    }

    private List<Checkbox> generateNewCheckBoxListBasedOnState(State state) {
        CheckboxDao checkboxDao = new CheckboxDao();
        return checkboxDao.findAllCheckBoxesBasedOnState(state);
    }

    private void revalidateLoadWindow() {
        scrollPane.getViewport().remove(listPanel);
        scrollPane.getViewport().add(generateLoadStatesPanel());
        this.revalidate();
        this.repaint();
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
