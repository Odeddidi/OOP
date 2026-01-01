//211913587 oded didi
package Collision;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import GameMain.Game;
import Hit.HitListener;
import Hit.HitNotifier;
import biuoop.DrawSurface;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Sprites.Ball;
import Sprites.Sprite;
import Sprites.Velocity;

/**
 * collision.Block class represents a block object in the game, inheriting from geometry.Rectangle and implementing
 * the collision.Collidable and sprites.Sprite interfaces.
 */
public class Block extends Rectangle implements Collidable, Sprite, HitNotifier {
    private final List<HitListener> hitListeners;
    private final Color color;

    /**
     * Constructs a collision.Block with specified color, upper-left corner, width, and height.
     *
     * @param color The color of the block.
     * @param upperLeft The upper-left corner point of the block.
     * @param width The width of the block.
     * @param height The height of the block.
     */
    public Block(Color color, Point upperLeft, int width, int height) {
        super(upperLeft, width, height);
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Returns the collision rectangle of the block.
     *
     * @return The rectangle representing the collision area of the block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    /**
     * Calculates the new velocity of the ball after it hits the block.
     * Handles collisions with block's sides and corners.
     *
     * @param collisionPoint The point where the ball collided with the block.
     * @param currentVelocity The current velocity of the ball.
     * @return The new velocity of the ball after the collision.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double upperY = this.getUpperLeft().getY();
        double lowerY = upperY + this.getHeight();
        double leftX = this.getUpperLeft().getX();
        double rightX = leftX + this.getWidth();
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        // Handle corner collisions
        if (Line.tresHold(collisionPoint.getY(), upperY)
                && Line.tresHold(collisionPoint.getX(), leftX)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }
        if (Line.tresHold(collisionPoint.getY(), upperY)
                && Line.tresHold(collisionPoint.getX(), rightX)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), -Math.abs(currentVelocity.getDy()));
        }
        if (Line.tresHold(collisionPoint.getX(), leftX)
                && Line.tresHold(collisionPoint.getY(), lowerY)) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }
        if (Line.tresHold(collisionPoint.getX(), rightX)
                && Line.tresHold(collisionPoint.getY(), lowerY)) {
            return new Velocity(Math.abs(currentVelocity.getDx()), Math.abs(currentVelocity.getDy()));
        }
        // Handle side collisions
        if (Line.tresHold(collisionPoint.getY(), upperY)) {
            // Collision with the top side
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }
        if (Line.tresHold(collisionPoint.getY(), lowerY)) {
            // Collision with the bottom side
            return new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));
        }
        if (Line.tresHold(collisionPoint.getX(), leftX)) {
            // Collision with the left side
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        if (Line.tresHold(collisionPoint.getX(), rightX)) {
            // Collision with the right side
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        // If collision point doesn't match any side, return unchanged velocity
        return new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
    }

    /**
     * Draws the block on the provided DrawSurface.
     *
     * @param surface The surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }

    /**
     * Method called to notify that time has passed.
     * Currently, this block does not change over time.
     */
    @Override
    public void timePassed() {
        // No implementation needed for now
    }

    /**
     * Adds this block to the game, registering it as both a sprite and a collidable.
     *
     * @param g The game to add this block to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Returns the color of the block.
     *
     * @return The color of the block.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Remove this block to the game, registering it as both a sprite and a collidable.
     *
     * @param g The game to remove this block to.
     */
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }
    /**
     * Checks if the color of the ball matches the color of this block.
     *
     * @param ball the ball to check
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor() == this.color;
    }
    /**
     * Adds the given HitListener to the list of listeners.
     *
     * @param hl the HitListener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * Removes the given HitListener from the list of listeners.
     *
     * @param hl the HitListener to remove
     */
    // Remove hl from the list of listeners to hit events.
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * Notifies all registered listeners about a hit event.
     *
     * @param hitter the ball that caused the hit event
     */
    private void notifyHit(Ball hitter) {
    // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
    // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}