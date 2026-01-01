//211913587 oded didi
package Collision;
import Geometry.Point;
import Geometry.Rectangle;
import Sprites.Ball;
import Sprites.Velocity;

/**
 * The collision.Collidable interface represents objects that can be collided with.
 * It provides methods to get the collision shape and to handle collisions.
 */
public interface Collidable {
    /**
     * Gets the shape of the collidable object.
     *
     * @return The collision rectangle of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Handles the collision with the object.
     * Computes the new velocity of an object after a collision.
     *
     * @param collisionPoint   The point where the collision occurred.
     * @param currentVelocity  The current velocity of the object.
     * @param hitter The ball who hit the object.
     * @return The new velocity after the collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}