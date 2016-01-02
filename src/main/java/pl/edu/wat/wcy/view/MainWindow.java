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
    public int mainWindowHeight = 500;

    private JTextField textField;
    private JButton generateBrowseWindowsButton;
    private MosaicWindow mosaicWindow;
    private JButton generateCheckBoxesButton;
    private JPanel checkBoxPanel;
    private java.util.List<CheckBoxPanel> checkBoxPanelList;
    public java.util.List<BrowseWindow> browseWindowList;
    public List<State> stateList;

    private JButton saveButton;
    private JButton loadButton;

    private LoadWindow loadWindow;

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        super("Main Window");

        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
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

        setMainWindowValues();
        setMainWindowLayout();
        add(inputPanel());
        add(statePanel());
        add(checkBoxPanel());
        add(generateBrowseWindowsButtonPanel());
        addMosaicWindow();
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
        Checkbox checkbox;

        for (CheckBoxPanel c : checkBoxPanelList) {
            checkbox = new Checkbox();
            //System.out.println("id: " + stateList.get(0).getId());
            System.out.println();
            checkbox.setState(stateList.get(0));
            checkbox.setHorizontal(c.horizontalBar.isSelected());
            checkbox.setVertical(c.verticalBar.isSelected());
            checkbox.setPath("1");
            //checkbox.setPath(browseWindowList.get(i).jTextField.getText());
            checkboxDao.create(checkbox);
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
        loadWindow = new LoadWindow(this);
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

                    mainWindowHeight += 90;
                    setSize(mainWindowWidth, mainWindowHeight);
                    centerWindow();

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

    private void generateNCheckBoxPanels(Integer numberOfCheckBoxPanelsToGenerate) {
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

    private void generateNBrowseWindows(Integer numberOfBrowseWindowsToGenerate) {
        int x = 0;
        int y = 0;
        int countX = 0;
        int countY = 0;
        for (int i = 0; i < numberOfBrowseWindowsToGenerate; i++) {

            IWindow browseWindow;
            if (checkBoxPanelList.get(i).horizontalBar.isSelected() && checkBoxPanelList.get(i).verticalBar.isSelected()) {
                browseWindow = new HorizontalBarDecorator(new VerticalBarDecorator(new BrowseWindow(this)));
            } else if (checkBoxPanelList.get(i).horizontalBar.isSelected()) {
                browseWindow = new HorizontalBarDecorator(new BrowseWindow(this));
            } else if (checkBoxPanelList.get(i).verticalBar.isSelected()) {
                browseWindow = new VerticalBarDecorator(new BrowseWindow(this));
            } else {
                browseWindow = new BrowseWindow(this);
            }
            browseWindow.setLocation(x + countX * 320, y + countY * 130);
            //browseWindow.enrichBrowseWindowList();

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
        System.out.println("painting mosaic from:" + path);
    }

    public void updateStateList(){
        StateDao stateDao = new StateDao();
        stateList = stateDao.findAll();
    }
}
