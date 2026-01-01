//211913587 oded didi
package GameMain;
import Hit.Counter;
import Hit.HitListener;
import Collision.Block;
import Sprites.Ball;

/**
 * A listener that tracks the score in a game.
 * The player receives points whenever the ball hits a block.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;

    /**
     * Constructs a ScoreTrackingListener with the specified score counter.
     *
     * @param scoreCounter the counter that tracks the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Updates the score when a block is hit.
     * Each hit adds 5 points to the current score.
     *
     * @param beingHit the block that was hit
     * @param hitter the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}