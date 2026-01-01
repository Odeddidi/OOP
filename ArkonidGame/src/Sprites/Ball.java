// 211913587 oded didi
package Sprites;
import GameMain.Game;
import GameMain.GameEnvironment;
import biuoop.DrawSurface;
import Collision.Collidable;
import Collision.CollisionInfo;
import Geometry.Line;
import Geometry.Point;

/**
 * A class representing a ball.
 * The ball has a center point, radius, color, and velocity,
 * and can move and detect collisions in a game environment.
 */
public class Ball implements Sprite {
    private Point center; // The center point of the ball
    private final int r; // The radius of the ball
    private  java.awt.Color color; // The color of the ball
    private Velocity v; // The velocity of the ball
    private GameEnvironment gameEnvironment; // The game environment for collision handling
    /**
     * Constructs a ball with the specified parameters.
     *
     * @param center The center point of the ball.
     * @param r      The radius of the ball.
     * @param color  The color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Gets the x-coordinate of the center of the ball.
     *
     * @return The x-coordinate of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the center of the ball.
     *
     * @return The y-coordinate of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the center point of the ball.
     *
     * @return The center point of the ball.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Gets the size (radius) of the ball.
     *
     * @return The size (radius) of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * Sets the color of the block.
     *
     * @param color The new color to be set for the block.
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface The DrawSurface on which to draw the ball.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle(this.getX(), this.getY(), r);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v The velocity to set.
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * Sets the velocity of the ball with the given dx and dy.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return The velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Updates the center of the ball based on its current velocity.
     * This method applies the current velocity to the center point,
     * effectively moving the ball to a new position.
     */
    public void changeCenter() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Moves the ball one step according to its velocity.
     * Checks for collisions and updates position and velocity accordingly.
     */
    public void moveOneStep() {
        // Case the velocity is not set
        if (this.v == null) {
            return;
        }
        CollisionInfo c = this.gameEnvironment.getClosestCollision(this.trajectory());

        // No object to hit
        if (c == null) {
            this.changeCenter();
            return;
        }
        // Collision detected
            Point p = c.collisionPoint();
            Collidable collidable = c.collisionObject();
            double newX = p.getX() - 0.2 * this.v.getDx();
            double newY = p.getY() - 0.2 * this.v.getDy();
            // Change the center to be slightly before the hit
            this.center = new Point(newX, newY);
            this.setVelocity(collidable.hit(this, p, this.v));
            // Handling stuck in paddle
            if (this.center.pointInsideRectangle(collidable.getCollisionRectangle())) {
                this.changeCenter();
            }
    }

    /**
     * Calculates the trajectory line of the ball based on its current velocity.
     *
     * @return The trajectory line of the ball.
     */
    public Line trajectory() {
        Point nextCenter = this.getVelocity().applyToPoint(this.center);
        return new Line(new Point(this.center.getX(), this.center.getY()),
                new Point(nextCenter.getX(), nextCenter.getY()));
    }

    /**
     * Sets the game environment for the ball.
     *
     * @param game The game environment to set.
     */
    public void setGameEnvironment(GameEnvironment game) {
        this.gameEnvironment = game;
    }

    /**
     * Updates the state of the ball, moving it one step.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Adds the ball to the given game as a sprite.
     *
     * @param g The game to which to add the ball.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Remove a ball from game.
     *
     * @param g The game to which to remove the ball.
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}