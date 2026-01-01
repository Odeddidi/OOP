//211913587 oded didi
package Sprites;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The sprites.SpriteCollection class manages a collection of sprites.Sprite objects.
 * It provides methods to add sprites, notify them of time passing, and draw them on a given surface.
 */
public class SpriteCollection {
    private final List<Sprite> sprites;


    /**
     * Constructs a new sprites.SpriteCollection with an empty list of sprites.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s The sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Remove a sprite from the collection.
     *
     * @param s The sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Calls the timePassed() method on all sprites in the collection.
     * This method is typically called to update the state of the sprites based on the passage of time.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the provided DrawSurface.
     * This method is called to render the sprites on the screen.
     *
     * @param d The surface on which the sprites are drawn.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}