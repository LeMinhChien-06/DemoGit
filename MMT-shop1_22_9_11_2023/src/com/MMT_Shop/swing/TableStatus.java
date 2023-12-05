package com.MMT_Shop.swing;

import com.MMT_Shop.model.StatusType;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class TableStatus extends JLabel {

    public StatusType getType() {
        return type;
    }

    public TableStatus() {
        setForeground(new Color(242, 242, 242));
    }
    private StatusType type;

    public void setType(StatusType type) {
        this.type = type;
        if (type == StatusType.CON) {
            setText("còn hàng");
        } else if (type == StatusType.SAPHET) {
            setText("Sắp hết");
        } else {
            setText("Hết hàng");
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (type != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gr;
            if (type == StatusType.CON) {
                gr = new GradientPaint(0, 0, new Color(142, 142, 250), 0, getHeight(), new Color(123, 123, 245));
            } else if (type == StatusType.SAPHET) {
                gr = new GradientPaint(0, 0, new Color(241, 208, 62), 0, getHeight(), new Color(211, 184, 61));
            } else {
                gr = new GradientPaint(0, 0, new Color(109, 92, 121), 0, getHeight(), new Color(195, 20, 50));
            }
            g2.setPaint(gr);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 1, 1);
        }
        super.paintComponent(g);
    }

}
