package pl.edu.wat.wcy.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MosaicWindow extends JPanel {
    private File fileToPaint;
    public String path = "";
    private BufferedImage imageToPaint;

    public MosaicWindow() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageToPaint != null) {
            drawMosaic(g);
        }
    }

    private void drawMosaic(Graphics g) {
        int imageWidth = getWidth() / 3;
        int imageHeight = getHeight() / 4;
        int x = 0;
        int y = 0;
        drawNTimesImageBeginIn(3, x, y, imageWidth, imageHeight, g);
        x -= imageWidth / 2;
        y += imageHeight;
        drawNTimesImageBeginIn(4, x, y, imageWidth, imageHeight, g);
        x = 0;
        y += imageHeight;
        drawNTimesImageBeginIn(3, x, y, imageWidth, imageHeight, g);
        x -= imageWidth / 2;
        y += imageHeight;
        drawNTimesImageBeginIn(4, x, y, imageWidth, imageHeight, g);
    }

    private void drawNTimesImageBeginIn(int N, int x, int y, int imageWidth, int imageHeight, Graphics g) {
        for (int i = 0; i < N; i++) {
            g.drawImage(imageToPaint, x + i * imageWidth, y, imageWidth, imageHeight, this);
        }
    }

    public void loadFile(String path) {
        this.path = path;
        fileToPaint = new File(path);
    }

    public void setImageToPaint() throws IOException {
        imageToPaint = ImageIO.read(fileToPaint);
    }
}
