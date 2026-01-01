//211913587 oded didi
package Hit;

import GameMain.Game;
import Collision.Block;
import Sprites.Ball;

/**
 * The BallRemover class is responsible for removing balls from the game when they hit a specific block
 * (usually a "death-region" block) and updating the counter that tracks the number of balls left in the game.
 */
public class BallRemover implements HitListener {
    private final Game game; // Reference to the game
    private final Counter counter; // Counter to track the remaining balls

    /**
     * Constructs a BallRemover.
     *
     * @param game    The game from which balls will be removed.
     * @param counter The counter that tracks the number of balls left in the game.
     */
    public BallRemover(Game game, Counter counter) {
        this.game = game;
        this.counter = counter;
    }

    /**
     * Called whenever a ball hits a block. This method removes the ball from the game and decreases the ball counter.
     *
     * @param beingHit The block that was hit.
     * @param hitter   The ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game
        hitter.removeFromGame(this.game);
        // Decrease the ball counter
        this.counter.decrease(1);
    }
}