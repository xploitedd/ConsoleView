package me.xploited.ConsoleView.components;

import java.awt.*;
import java.awt.image.ImageObserver;

public class SpriteComponent extends DisplayComponent<Image> {

    private Point higher;
    private Point lower;
    private int size;

    /**
     * Creates a new sprite component
     * @param obj Image where is the sprite that represents the component
     * @param higher Highest point of the sprite on the image
     * @param lower Lowest point of the sprite on the image
     * @param size size of the sprite to be printed
     */
    public SpriteComponent(Image obj, Point higher, Point lower, int size) {
        super(obj);

        this.higher = higher;
        this.lower = lower;
        this.size = size;
    }

    /**
     * Sets a new sprite for the component
     * @param img Image where the sprite is located
     * @param higher Highest point of the sprite
     * @param lower Lowest point of the sprite
     */
    public void set(Image img, Point higher, Point lower) {
        super.set(img);

        this.higher = higher;
        this.lower = lower;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g, int x, int y, int w, int h) {
        g.drawImage(obj, x, y, x + size * w, y + size * w, higher.x, higher.y, lower.x, lower.y, new Observer());
    }

    private static class Observer implements ImageObserver {

        @Override
        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
            return false;
        }

    }

}
