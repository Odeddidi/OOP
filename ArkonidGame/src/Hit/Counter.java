//211913587 oded didi
package Hit;

/**
 * The Counter class provides a simple mechanism to keep track of a count.
 * It supports operations to increase or decrease the count and retrieve the current count value.
 */
public class Counter {
    private int count; // The current count

    /**
     * Constructs a new Counter with an initial count of 0.
     */
    public Counter() {
        count = 0;
    }

    /**
     * Increases the current count by the specified number.
     *
     * @param number The number to add to the current count.
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Decreases the current count by the specified number.
     *
     * @param number The number to subtract from the current count.
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * Returns the current count.
     *
     * @return The current count value.
     */
    public int getValue() {
        return count;
    }
}