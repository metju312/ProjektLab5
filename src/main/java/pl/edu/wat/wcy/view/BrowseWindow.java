package pl.edu.wat.wcy.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BrowseWindow extends Window {

    public JTextField jTextField;
    private JButton FileChooserButton;
    private JButton generateMosaicButton;
    private MainWindow mainWindow;

    public BrowseWindow(MainWindow mainWindow) {
        super();
        setTitle("Browse Window");
        this.mainWindow = mainWindow;
        setBrowseWindowValues();
        setBrowseWindowLayout();
        generatePanels();
        setBrowseWindowVisible();
    }

    private void generatePanels() {
        buildJTextFieldWithLength(20);
        buildJButtonWithText("...");
        buildGenerateMosaicButtonWithText("Generate Mosaic");
        addComponentsVertical(mainPanel, upPanel(), downPanel());
    }

    private void addComponentsHorizontal(JPanel panel, Component... components) {
        addComponents(panel, BoxLayout.X_AXIS, components);
    }

    private void addComponentsVertical(JPanel panel, Component... components) {
        addComponents(panel, BoxLayout.Y_AXIS, components);
    }

    private void addComponents(JPanel panel, int axis, Component... components) {
        panel.setLayout(new BoxLayout(panel, axis));

        for (Component component : components) {
            panel.add(component);
        }
    }

    private JPanel downPanel() {
        JPanel panel = new JPanel();
        panel.add(generateMosaicButton);
        return panel;
    }

    private JPanel upPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Input path"));
        panel.add(jTextField);
        panel.add(FileChooserButton);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    private void setBrowseWindowLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void buildGenerateMosaicButtonWithText(String buttonText) {
        generateMosaicButton = new JButton(buttonText);
        generateMosaicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainWindow.repaintMosaic(jTextField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(generateMosaicButton, "Wrong path");
                }
            }
        });
    }

    private void setBrowseWindowVisible() {

        setVisible(true);
    }

    private void setBrowseWindowValues() {
        setSize(320, 130);
    }

    private void buildJTextFieldWithLength(int length){
        jTextField = new JTextField(length);
        jTextField.setMaximumSize(new Dimension(200, 40));
    }

    private void buildJButtonWithText(String buttonText) {
        FileChooserButton = new JButton(buttonText);
        FileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFileChooserWindow();
            }
        });
    }

    private void generateFileChooserWindow() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        String imagePath = jFileChooser.getSelectedFile().getAbsolutePath();
        jTextField.setText(imagePath);
    }

    @Override
    public String getPath() {
        return "" + jTextField.getText();
    }

    @Override
    public void disposeMe() {
        dispose();
    }
}
