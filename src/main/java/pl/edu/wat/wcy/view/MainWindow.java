package pl.edu.wat.wcy.view;

import pl.edu.wat.wcy.model.dao.CheckboxDao;
import pl.edu.wat.wcy.model.dao.StateDao;
import pl.edu.wat.wcy.model.entities.*;
import pl.edu.wat.wcy.model.entities.Checkbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MainWindow extends JFrame{
    private static MainWindow instance = null;

    public int mainWindowWidth = 400;
    public int mainWindowHeight = 600;

    public JTextField textField;
    public JButton generateBrowseWindowsButton;
    private MosaicWindow mosaicWindow;
    public JButton generateCheckBoxesButton;
    private JPanel checkBoxPanel;
    public java.util.List<CheckBoxPanel> checkBoxPanelList;
    public List<IWindow> browseWindowList;
    public List<State> stateList;

    public JButton saveButton;
    private JButton loadButton;

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        super("Main Window");
        setLookAndFeel();
        setMainWindowValues();
        setMainWindowLayout();
        setBrowseWindowList();

        add(inputPanel());
        add(statePanel());
        add(checkBoxPanel());
        add(generateBrowseWindowsButtonPanel());
        addMosaicWindow();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void setBrowseWindowList() {
        browseWindowList = new ArrayList<>();
    }

    private JPanel statePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        buildSaveButtonWithText("Save");
        panel.add(saveButton);

        buildLoadButtonWithText("Load");
        panel.add(loadButton);

        panel.setBorder(BorderFactory.createTitledBorder("State Panel"));
        return panel;
    }

    private void buildSaveButtonWithText(String save) {
        saveButton = new JButton(save);
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveState();
            }
        });
    }

    private void saveState() {
        StateDao stateDao = new StateDao();
        State state = new State();
        state.setPath(mosaicWindow.path);
        stateDao.create(state);
        updateStateList();
        CheckboxDao checkboxDao = new CheckboxDao();

        int i = 0;
        for (CheckBoxPanel c : checkBoxPanelList) {
            Checkbox checkbox = new Checkbox();
            checkbox.setPath(browseWindowList.get(i).getPath());
            checkbox.setHorizontal(c.getHorizontalValue());
            checkbox.setVertical(c.getVerticalValue());
            checkbox.setState(state);
            checkboxDao.create(checkbox);
            i++;
        }

    }

    private void buildLoadButtonWithText(String load) {
        loadButton = new JButton(load);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateLoadWindow();
            }
        });
    }

    private void generateLoadWindow() {
        new LoadWindow(this);
    }

    private JPanel generateBrowseWindowsButtonPanel() {
        JPanel panel = new JPanel();
        panel.add(generateBrowseWindowsButton);
        return panel;
    }

    private JPanel inputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        buildTextField();
        panel.add(textField);

        buildGenerateCheckBoxesButtonWithText("Generate Check Boxes");
        panel.add(generateCheckBoxesButton);

        panel.setBorder(BorderFactory.createTitledBorder("Input Box"));
        return panel;
    }

    private void buildTextField() {
        textField = new JTextField();
        textField.setMaximumSize(new Dimension(220, 40));
    }

    private JScrollPane checkBoxPanel() {
        checkBoxPanel = new JPanel();

        WrapLayout layout = new WrapLayout();
        checkBoxPanel.setLayout(layout);

        JScrollPane pane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setViewportView(checkBoxPanel);

        checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Check Boxes Panel"));

        buildGenerateBrowseWindowsButtonWithText("Generate Browse Windows");
        return pane;
    }

    private void setMainWindowLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void addMosaicWindow() {
        mosaicWindow = new MosaicWindow();
        mosaicWindow.setBorder(BorderFactory.createTitledBorder("Mosaic"));
        mosaicWindow.setPreferredSize(new Dimension(200, 200));
        mosaicWindow.setMinimumSize(new Dimension(200, 200));
        add(mosaicWindow);
    }

    private void buildGenerateCheckBoxesButtonWithText(String buttonText) {
        generateCheckBoxesButton = new JButton(buttonText);
        generateCheckBoxesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generateNCheckBoxPanels(Integer.parseInt(textField.getText()));
                    textField.setEnabled(false);
                    generateCheckBoxesButton.setEnabled(false);
                    checkBoxPanel.revalidate();
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(checkBoxPanel, "Wrong argument");
                }
            }
        });
    }

    public void generateNCheckBoxPanels(Integer numberOfCheckBoxPanelsToGenerate) {
        checkBoxPanelList = new ArrayList<>(numberOfCheckBoxPanelsToGenerate);
        for (int i = 0; i < numberOfCheckBoxPanelsToGenerate; i++) {
            CheckBoxPanel panel = new CheckBoxPanel();
            checkBoxPanelList.add(panel);
            checkBoxPanel.add(panel);
        }
    }

    private void buildGenerateBrowseWindowsButtonWithText(String buttonText) {
        generateBrowseWindowsButton = new JButton(buttonText);
        generateBrowseWindowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generateNBrowseWindows(Integer.parseInt(textField.getText()));

                    generateCheckBoxesButton.setEnabled(false);
                    textField.setEnabled(false);
                    saveButton.setEnabled(true);
                    generateBrowseWindowsButton.setEnabled(false);
                    for (CheckBoxPanel panel : checkBoxPanelList) {
                        panel.horizontalBar.setEnabled(false);
                        panel.verticalBar.setEnabled(false);
                    }
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void setMainWindowValues() {
        setSize(mainWindowWidth, mainWindowHeight);
        centerWindow();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public void generateNBrowseWindows(Integer numberOfBrowseWindowsToGenerate) {
        int x = 0;
        int y = 0;
        int countX = 0;
        int countY = 0;
        for (int i = 0; i < numberOfBrowseWindowsToGenerate; i++) {

            IWindow browseWindow;
            if (checkBoxPanelList.get(i).horizontalBar.isSelected() && checkBoxPanelList.get(i).verticalBar.isSelected()) {
                browseWindow = new HorizontalBarDecorator(new VerticalBarDecorator(new BrowseWindow(this)));
                browseWindowList.add(browseWindow);
            } else if (checkBoxPanelList.get(i).horizontalBar.isSelected()) {
                browseWindow = new HorizontalBarDecorator(new BrowseWindow(this));
                browseWindowList.add(browseWindow);
            } else if (checkBoxPanelList.get(i).verticalBar.isSelected()) {
                browseWindow = new VerticalBarDecorator(new BrowseWindow(this));
                browseWindowList.add(browseWindow);
            } else {
                browseWindow = new BrowseWindow(this);
                browseWindowList.add(browseWindow);
            }
            browseWindow.setLocation(x + countX * 320, y + countY * 130);

            countY++;
            if (countY == 5) {
                countY = 0;
                countX++;
            }
            if (countX == 4) {
                countX = 0;
            }
        }

    }

    public void repaintMosaic(String path) throws IOException {
        mosaicWindow.loadFile(path);
        mosaicWindow.setImageToPaint();
        mosaicWindow.repaint();
    }

    public void updateStateList(){
        StateDao stateDao = new StateDao();
        stateList = stateDao.findAll();
        System.out.println("Actual stateList: ");
        for (State state : stateList) {
            System.out.println(state.getDate());
        }

    }

    public void resetMainWindow(){
        closeBrowseWindows();
        this.getContentPane().removeAll();
        add(inputPanel());
        add(statePanel());
        add(checkBoxPanel());
        add(generateBrowseWindowsButtonPanel());
        addMosaicWindow();
        generateBrowseWindowsButton.setEnabled(true);
        this.revalidate();
        this.repaint();

    }

    public void closeBrowseWindows() {
        for (IWindow window : browseWindowList) {
            window.disposeMe();
        }
    }

    public void setCheckBoxesValues(List<Checkbox> checkBoxList) {
        int i = 0;
        for (CheckBoxPanel panel : checkBoxPanelList) {
            panel.horizontalBar.setSelected(checkBoxList.get(i).isHorizontal());
            panel.verticalBar.setSelected(checkBoxList.get(i).isVertical());
            i++;
        }
    }

    public void setBrowseWindowsPaths(List<Checkbox> checkBoxList) {
        int i = 0;
        for (IWindow window : browseWindowList) {
            window.setPath(checkBoxList.get(i).getPath());
            i++;
        }
    }
}
