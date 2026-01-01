//211913587 oded didi
package Hit;

import Collision.Block;
import Sprites.Ball;

/**
 * The HitListener interface should be implemented by classes that need to be notified
 * of hit events, i.e., when a block is hit by a ball.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object (a block) is hit by the hitter (a ball).
     *
     * @param beingHit The block that is being hit.
     * @param hitter The ball that hit the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}