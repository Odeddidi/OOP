//211913587 oded didi
package Sprites;

import Geometry.Point;

/**
 * The sprites.Velocity class represents the change in position (dx, dy) for a moving object.
 */
public class Velocity {
    private final double dx;
    private final double dy;

    /**
     * Constructs a sprites.Velocity with the specified change in x (dx) and change in y (dy).
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Applies this velocity to a given point and returns a new point with updated coordinates.
     *
     * @param p The point to which the velocity will be applied.
     * @return A new geometry.Point with the updated coordinates (x + dx, y + dy).
     */
    public Point applyToPoint(Point p) {
        double x = (p.getX() + dx);
        double y = (p.getY() + dy);
        return new Point(x, y);
    }

    /**
     * Returns the change in x-coordinate (dx).
     *
     * @return The change in x-coordinate.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the change in y-coordinate (dy).
     *
     * @return The change in y-coordinate.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Creates a sprites.Velocity object from an angle and a speed.
     *
     * @param angle The direction of the velocity in degrees.
     * @param speed The magnitude of the velocity.
     * @return A new sprites.Velocity object with the calculated dx and dy based on the angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }
    /**
     * Calculates the speed of the ball based on its current velocity.
     *
     * @return The speed of the ball.
     */
    public double speed() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}