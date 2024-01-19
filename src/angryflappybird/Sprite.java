/**
 * Team Cat - Sprite Class
 * Modified by...
 * @author Lynn and Claudia
 */

package angryflappybird;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a generic object
 */
public abstract class Sprite {

    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean collision; // collision state

    public Sprite() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public Sprite(double pX, double pY, Image image) {
        setPositionXY(pX, pY);
        setImage(image);
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setImage(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void setPositionXY(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void addVelocity(double x, double y) {
        this.velocityX += x;
        this.velocityY += y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getWidth() {
        return width;
    }
    
    public boolean getCollision() {
        return collision;
    }
    
    /**
     * Sets the height
     * @param n new height
     * @author Claudia
     */
    public void setHeight(int n) {
        height = n;
    }
    
    /**
     * Sets the width
     * @param n new width
     * @author Claudia
     */
    public void setWidth(int n) {
        width = n;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersectsSprite(Sprite s) {
        collision = s.getBoundary().intersects(this.getBoundary());
        return collision;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }
}