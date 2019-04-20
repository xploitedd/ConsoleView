package isel.leic.pg.console;

import isel.leic.pg.concurrent.*;
import me.xploited.ConsoleView.ConsoleView;

import java.awt.event.*;

public class KeyManager implements KeyListener {

    private static final int KEYS_BUFFER_SIZE = 128;
    private static final int PRESSED_KEYS_BUFFER_SIZE = 32;

    private final CharRingBuffer typedChars = new CharRingBuffer(KEYS_BUFFER_SIZE,true);
    private final PositiveIntSet pressedKeys = new PositiveIntSet(PRESSED_KEYS_BUFFER_SIZE);
    private final ConsoleView view;

    public KeyManager(ConsoleView view) { this.view = view; }

    public char getChar(long timeout) throws InterruptedException { return typedChars.get(timeout); }

    public boolean isPressed(int code) { return pressedKeys.contains(code); }

    public boolean anyPressed() {
        return !pressedKeys.isEmpty();
    }

    public int getAnyPressed(long timeout) throws InterruptedException { return pressedKeys.getAny(timeout); }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (view.isEcho() && isPrintable(c))
            view.printChar(c);

        typedChars.put(c);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    private boolean isPrintable(char c) {
        return Character.isDefined(c);
    }

    public void clearPressedKeys() {
        pressedKeys.clear();
    }

}
