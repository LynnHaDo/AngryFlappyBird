/**
 * @author Mina
 */
package angryflappybird;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a bird
 */
public class Fish extends Sprite {
    private boolean collision;
    
    /**
     * Constructor for the bird (fish)
     * @param pX initial x position
     * @param pY initial y position
     * @param image image to use
     */
    public Fish(double pX, double pY, Image image) {
        super(pX, pY, image);
    }
    
    /**
     * Set the collision state of the fish
     * @param collisionState true if the fish hits an obstacle, false otherwise
     */
    public void setCollision(boolean collisionState) {
        collision = collisionState;
    }
    
    
}