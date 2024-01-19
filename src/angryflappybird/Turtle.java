/**
 * Team Cat - Turtle Sprite
 * @author Claudia
 */
package angryflappybird;

import javafx.scene.image.Image;

/**
 * Represents a pig (is now  a turtle)
 * Extends Sprite Abstract Class
 * @author Claudia
 */
public class Turtle extends Sprite {
    /**
     * Constructor for the bird (fish)
     * @param pX initial x position
     * @param pY initial y position
     * @param image image to use
     */
    public Turtle(double pX, double pY, Image image) {
        super(pX, pY, image);
    }
}
