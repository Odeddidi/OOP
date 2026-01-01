//211913587 oded didi
package Hit;
import GameMain.Game;
import Collision.Block;
import Sprites.Ball;

/**
 * The BlockRemover class is responsible for removing blocks from the game when they are hit
 * and updating the counter that tracks the number of blocks left in the game.
 */
public class BlockRemover implements HitListener {
    private final Game game; // Reference to the game
    private final Counter remainingBlocks; // Counter to track the remaining blocks

    /**
     * Constructs a BlockRemover.
     *
     * @param game           The game from which blocks will be removed.
     * @param remainingBlocks The counter that tracks the number of blocks left in the game.
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Called whenever a block is hit by a ball. This method removes the block from the game,
     * unregisters this listener from the block, updates the color of the ball, and decreases the block counter.
     *
     * @param beingHit The block that was hit.
     * @param hitter   The ball that hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the block from the game
        beingHit.removeFromGame(this.game);
        // Remove this listener from the block
        beingHit.removeHitListener(this);
        // Set the color of the ball to the color of the block
        hitter.setColor(beingHit.getColor());
        // Decrease the block counter
        this.remainingBlocks.decrease(1);
    }
}