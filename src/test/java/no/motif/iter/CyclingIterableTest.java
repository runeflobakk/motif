package no.motif.iter;

import static no.motif.Ints.increment;
import static no.motif.Iterate.none;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CyclingIterableTest {


    @Test
    public void repeatingNoElementsYieldsNoElementsForAnyRepeatAmount() {
        assertThat(none().repeat(10), emptyIterable());
        assertThat(none().repeat(0), emptyIterable());
        assertThat(none().repeat(-1), emptyIterable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void repeatingSomeElementsANegativeAmountIsNotValid() {
        on(1, 2, 3).repeat(-4);
    }

    @Test
    public void repeatingZeroTimesYieldsNoElements() {
        assertThat(on(new SuccessorIterable<>(0, increment)).repeat(0), emptyIterable());
    }

    @Test
    public void repeatingOneTimeYieldsTheSameElements() {
        assertThat(on("a", "b", "c").repeat(1), contains("a", "b", "c"));
    }

    @Test
    public void repeatingWillCycleTheIterableAGivenAmountOfTimes() {
        assertThat(on("x", "y").repeat(3), contains("x", "y", "x", "y", "x", "y"));
    }
}
