// 211913587 oded didi
package Sprites;

import GameMain.Game;
import biuoop.DrawSurface;
import Hit.Counter;
import java.awt.Color;

/**
 * The ScoreIndicator class is a sprite that displays the current score on the screen.
 * It updates the score display based on the value of the Counter it holds.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score; // Counter to track the current score

    /**
     * Constructs a ScoreIndicator with the specified score counter.
     *
     * @param score The Counter object that tracks the score.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Draws the score on the provided DrawSurface.
     * The score is displayed at the top center of the screen.
     *
     * @param d The DrawSurface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(395, 15, "score " + this.score.getValue(), 15);
    }

    /**
     * Adds this ScoreIndicator to the game as a sprite.
     *
     * @param g The game to which this sprite is added.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Called to notify the sprite that time has passed.
     * In this case, no specific action is needed for time updates.
     */
    @Override
    public void timePassed() {
        // No action required on time passed for the score indicator
    }
}