package me.xploited.ConsoleView.components;

import java.awt.*;

public class CharComponent extends DisplayComponent<Character> {

    /**
     * Buffer to print the character using {@code drawChars}
     */
    private static final char[] buffer = new char[1];
    private static final int WIDTH_FACTOR = 20;
    private static final int HEIGHT_FACTOR = 6;

    private Color bgColor;
    private Color fgColor;

    /**
     * Creates a new char component
     * @param obj char that represents this component
     * @param bgColor char background color
     * @param fgColor char foreground color
     */
    public CharComponent(char obj, Color bgColor, Color fgColor) {
        super(obj);

        this.bgColor = bgColor;
        this.fgColor = fgColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g, int x, int y, int w, int h) {
        // draw the background
        g.setColor(bgColor);
        g.fillRect(x, y, w, h);

        // draw the character
        g.setColor(fgColor);
        FontMetrics fm = g.getFontMetrics();
        int dx = fm.charWidth(obj), dy = fm.getAscent();
        /* uses absolute value to compute the x and y because if the weight of the block is less
         than the height of the font then it would give a negative value*/
        buffer[0] = obj;
        g.drawChars(buffer, 0, 1, x + (w - dx) / 2 - dx / WIDTH_FACTOR, y + (h + dy) / 2 - dy / HEIGHT_FACTOR);
    }

    /**
     * Sets the background of this character
     * @param bgColor character background
     */
    public void setBackground(Color bgColor) { this.bgColor = bgColor; }

    /**
     * Sets the foreground of this character
     * @param fgColor character foreground
     */
    public void setForeground(Color fgColor) { this.fgColor = fgColor; }

}
