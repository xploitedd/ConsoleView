package me.xploited.ConsoleView;

import isel.leic.pg.Console;
import isel.leic.pg.concurrent.PositiveIntSet;
import isel.leic.pg.console.KeyManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConsoleView {

    private static final int MIN_TIME = 10;
    private static final int FONT_SIZE = 25;
    private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE);

    private JFrame frame;
    private DisplayBox displayBox;
    private String windowName;
    private BufferedImage sprites;
    private KeyManager keyManager;
    private Color bgColor = Color.BLACK;
    private Color fgColor = Color.WHITE;
    private boolean echo = false;

    /**
     * A class to take care of the console frame
     * @param lines console lines
     * @param columns console columns
     */
    public ConsoleView(int lines, int columns) {
        this("ConsoleView", lines, columns, "sprites.jpg");
    }

    /**
     * A class to take care of the console frame
     * @param windowName name of the console window
     * @param lines console lines
     * @param columns console columns
     * @param imageFile the sprites file to load
     */
    public ConsoleView(String windowName, int lines, int columns, String imageFile) {
        this.windowName = windowName;

        try {
            this.sprites = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the window
        frame = new JFrame(windowName);

        displayBox = new DisplayBox(lines, columns, FONT);
        frame.add(displayBox);
        frame.addKeyListener(keyManager = new KeyManager(this));
        frame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                keyManager.clearPressedKeys();
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();

        // display the window
        frame.setVisible(true);
    }

    /**
     * Sets current background
     * @param color background color
     */
    public void setBackground(Color color) { this.bgColor = color; }

    /**
     * Sets current foreground
     * @param color foreground color
     */
    public void setForeground(Color color) { this.fgColor = color; }

    /**
     * Sets cursor position
     * @param cl cursor line
     * @param cc cursor column
     */
    public void setCursor(int cl, int cc) { displayBox.setCursor(cl, cc); }

    /**
     * Gets current cursor line
     * @return cursor line
     */
    public int getLine() { return displayBox.getLine(); }

    /**
     * Gets current cursor column
     * @return cursor column
     */
    public int getColumn() { return displayBox.getColumn(); }

    /**
     * Checks for key echo
     * @return True if keys are being written to the console, false otherwise
     */
    public boolean isEcho() { return echo; }

    /**
     * Sets whether keys are being echoed to the console
     * @param echo true to allow console writing, false otherwise
     */
    public void setEcho(boolean echo) { this.echo = echo; }

    /**
     * Gets the char pressed
     * @return char pressed
     * @throws InterruptedException if timeout is interrupted
     */
    public char getChar() throws InterruptedException { return keyManager.getChar(MIN_TIME); }

    /**
     * Checks if a key is being pressed
     * @param code key to be checked
     * @return true if it's being pressed, false otherwise
     */
    public boolean isKeyPressed(int code) { return keyManager.isPressed(code); }

    /**
     * Checks if any key is being pressed
     * @return true if detects a key press, false otherwise
     */
    public boolean isAnyKeyPressed() { return keyManager.anyPressed(); }

    /**
     * Gets the current key pressed
     * @return key code if it was pressed within MIN_TIME, NO_KEY if timeout
     */
    public int getKeyPressed() {
        int key = 0;
        try {
            long tm = System.currentTimeMillis();
            key = keyManager.getAnyPressed(MIN_TIME);
            sleepRemainder(tm, MIN_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return key != PositiveIntSet.NO_ELEM ? key : Console.NO_KEY;
    }

    /**
     * Prints a character on the console window
     * @param c char to be printed
     */
    public void printChar(char c) { displayBox.add(c, bgColor, fgColor); }

    /**
     * Prints a string on the console window
     * @param s string to be printed
     */
    public void print(String s) {
        for (int i = 0; i < s.length(); i++)
            printChar(s.charAt(i));
    }

    /**
     * Draws a sprite on the console window
     * Notice that the higher point has x and y inferior to the lower point x and y
     * @param higher Higher point of the sprite
     * @param lower Lower point of the sprite
     * @param size Size for the sprite square
     * @return true if the sprite was printed, false otherwise
     */
    public boolean printSprite(Point higher, Point lower, int size) {
        if (sprites == null)
            return false;

        displayBox.add(sprites, higher, lower, size);
        return true;
    }

    /**
     * Disposes of the window
     */
    public void dispose() {
        frame.dispose();
        frame = null;
    }

    /**
     * Sleeps the remainder
     * @param startTm sleeping start time
     * @param totalTm sleeping time
     * @throws InterruptedException if the sleep was interrupted
     */
    private static void sleepRemainder(long startTm, long totalTm) throws InterruptedException {
        long elapsed = System.currentTimeMillis() - startTm;
        if (elapsed < totalTm)
            Thread.sleep(totalTm - elapsed);
    }

}
