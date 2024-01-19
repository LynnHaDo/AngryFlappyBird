/**
 * Team Cat - Floor Sprite
 * @author Claudia
 */
package angryflappybird;

import javafx.scene.image.Image;

/**
 * Represents the floor
 * Extends Sprite Abstract Class
 * @author Claudia
 */
public class Floor extends Sprite {
    /**
     * Constructor for the floor
     * @param pX initial x position
     * @param pY initial y position
     * @param image image to use
     */
    public Floor(double pX, double pY, Image image) {
        super(pX, pY, image);
    }
}
