package me.xploited.ConsoleView;

import me.xploited.ConsoleView.components.CharComponent;
import me.xploited.ConsoleView.components.DisplayComponent;
import me.xploited.ConsoleView.components.SpriteComponent;

import javax.swing.*;
import java.awt.*;

public class DisplayBox extends JComponent {

    private final DisplayComponent[][] components;
    private final int fontSize;
    private final int lines;
    private final int columns;
    private int cl = 0;
    private int cc = 0;

    /**
     * Creates the DisplayBox component
     * @param lines lines of the display box
     * @param columns columns of the display box
     * @param font font of each character (also determines the size of each square)
     */
    public DisplayBox(int lines, int columns, Font font) {
        this.lines = lines;
        this.columns = columns;
        this.fontSize = font.getSize();

        components = new DisplayComponent[lines][columns];

        setPreferredSize(new Dimension(columns * fontSize, lines * fontSize));
        setFont(font);
    }

    /**
     * {@inheritDoc}
     * @param g Window Graphics
     */
    @Override
    public void paint(Graphics g) {
        Rectangle rect = g.getClipBounds();
        int dx = getWidth() / columns, dy = getHeight() / lines;
        int x, y = 0;
        for (int i = 0; i < lines; i++, y += dy) {
            x = 0;
            for (int j = 0; j < columns; j++, x += dx) {
                if (rect.intersects(x, y, dx, dy)) {
                    if (hasComponent(i, j))
                        components[i][j].paint(g, x, y, dx, dy);
                }
            }
        }
    }

    /**
     * Sets the cursor if the position is available
     * @param cl cursor line
     * @param cc cursor column
     */
    public void setCursor(int cl, int cc) {
        if (cl >= lines || cl < 0 || cc >= columns || cc < 0)
            return;

        this.cl = cl;
        this.cc = cc;
    }

    /**
     * Checks if the position has an element in it
     * @param line line to be checked
     * @param column column to be checked
     * @return true if it has an element, false if not
     */
    public boolean hasComponent(int line, int column) {
        if (line >= lines || line < 0 || column >= columns || column < 0)
            return false;

        return components[line][column] != null;
    }

    /**
     * Sets a character in the display box
     * @param c characted to be set
     * @param line line of the char
     * @param column column of the char
     * @param bgColor background color for the position
     * @param fgColor foreground color of the character
     */
    public void set(char c, int line, int column, Color bgColor, Color fgColor) {
        if (c == '\n') {
            cc = 0;
            ++cl;
            return;
        }

        if (hasComponent(line, column)) {
            DisplayComponent dc = components[line][column];
            if (dc instanceof CharComponent) {
                CharComponent charc = (CharComponent) components[line][column];
                charc.setBackground(bgColor);
                charc.setForeground(fgColor);
                charc.set(c);
            } else {
                components[line][column] = new CharComponent(c, bgColor, fgColor);
            }
        } else {
            components[line][column] = new CharComponent(c, bgColor, fgColor);
        }

        repaint(column * fontSize, line * fontSize, fontSize, fontSize);
    }

    /**
     * Sets a sprite in the display box
     * @param img Image where the sprite is located
     * @param line line of the sprite on the display box
     * @param column column of the sprite on the display box
     * @param higher highest point of the sprite on the image
     * @param lower lowest point of the sprite on the image
     * @param size sprite square size
     */
    public void set(Image img, int line, int column, Point higher, Point lower, int size) {
        if (hasComponent(line, column)) {
            DisplayComponent dc = components[line][column];
            if (dc instanceof SpriteComponent) {
                SpriteComponent spritec = (SpriteComponent) components[line][column];
                spritec.set(img);
            } else {
                components[line][column] = new SpriteComponent(img, higher, lower, size);
            }
        } else {
            components[line][column] = new SpriteComponent(img, higher, lower, size);
        }

        repaint(column * fontSize, line * fontSize, size, size);
    }

    /**
     * Adds a character to the display box
     * @param c character to add
     * @param bgColor background color
     * @param fgColor foreground color
     */
    public void add(char c, Color bgColor, Color fgColor) {
        int column = jumpCursor();
        set(c, cl, column, bgColor, fgColor);
    }

    /**
     * Adds a sprite to the display box
     * @param img Image where the sprite is located
     * @param higher highest point of the sprite on the image
     * @param lower lowest point of the sprite on the image
     * @param size sprite square size
     */
    public void add(Image img, Point higher, Point lower, int size) {
        int column = jumpCursor();
        set(img, cl, column, higher, lower, size);
    }

    /**
     * Gets the current line
     * @return current line
     */
    public int getLine() { return cl; }

    /**
     * Gets the current column
     * @return current column
     */
    public int getColumn() { return cc; }

    /**
     * Jumps the cursor to the next position and returns the column of that position
     * @return Column of the new position
     */
    private int jumpCursor() {
        if (cc == columns) {
            if (cl + 1 == lines)
                cl = 0;
            else
                ++cl;

            cc = 0;
        }

        return cc++;
    }

}
