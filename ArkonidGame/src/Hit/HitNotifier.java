//211913587 oded didi
package Hit;

/**
 * The HitNotifier interface defines methods for adding and removing
 * HitListener objects. Classes that implement this interface can notify
 * listeners of hit events.
 */
public interface HitNotifier {

    /**
     * Add the given HitListener as a listener to hit events.
     *
     * @param hl the HitListener to be added
     */
    void addHitListener(HitListener hl);

    /**
     * Remove the given HitListener from the list of listeners to hit events.
     *
     * @param hl the HitListener to be removed
     */
    void removeHitListener(HitListener hl);
}