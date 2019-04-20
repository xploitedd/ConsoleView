package me.xploited.ConsoleView.components;

import java.awt.*;

public abstract class DisplayComponent<T> {

    protected T obj;

    /**
     * Constructs a new display component
     * @param obj Object of this component
     */
    public DisplayComponent(T obj) {
        this.obj = obj;
    }

    /**
     * Sets the component object
     * @param obj Object to be set
     */
    public void set(T obj) { this.obj = obj; }

    /**
     * Gets the component object
     * @return component object
     */
    public T get() { return obj; }

    /**
     * Paints the component object in the screen
     * @param g Window Graphics
     * @param x X position for the component
     * @param y Y position for the component
     * @param w width of the component
     * @param h height of the component
     */
    public abstract void paint(Graphics g, int x, int y, int w, int h);

}
