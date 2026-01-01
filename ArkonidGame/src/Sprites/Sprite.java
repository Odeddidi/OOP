//211913587 oded didi
package Sprites;
import biuoop.DrawSurface;

/**
 * The sprites.Sprite interface represents game objects that can be drawn to the screen
 * and have time-based behavior. Sprites are responsible for rendering themselves
 * on the game screen and updating their state as time passes.
 */
public interface Sprite {
    /**
     * Draws the sprite on the provided drawing surface.
     * This method is called during the game loop to render the sprite on the screen.
     *
     * @param d the drawing surface on which the sprite should be drawn
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed.
     * This method is called during the game loop to update the sprite's state.
     */
    void timePassed();
}