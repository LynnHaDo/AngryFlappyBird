/**
 * @author Lynn
 */
package angryflappybird;

import javafx.scene.image.Image;

/**
 * Represents an egg
 */
public class Egg extends Sprite {
    /** * True if the egg is pink/normal, false otherwise */
    private boolean isNormalEgg;
    
    /**
     * Constructor for the egg
     * @param pX initial x position
     * @param pY initial y position
     * @param image image to use
     */
    public Egg(double pX, double pY, Image image) {
        super(pX, pY, image);
        this.isNormalEgg = true;
    }

    /**
     * Get the isNormalEgg attribute
     * @return the isNormalEgg attribute value
     */
    public boolean isNormalEgg() {
        return isNormalEgg;
    }

    /**
     * Set the isNormalEgg attribute
     * @param n true if the egg (pearl) is yellow/normal, false otherwise
     */
    public void setNormalEgg(boolean n) {
        this.isNormalEgg = n;
    }
}