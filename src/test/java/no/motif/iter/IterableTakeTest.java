package no.motif.iter;

import static no.motif.Base.equalOrGreaterThan;
import static no.motif.Base.equalOrLessThan;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IterableTakeTest {

    @Test public void takingMoreElementsThanContainedYieldsAllElements() {
        assertThat(on(1, 2, 3).take(4), contains(1, 2, 3));
    }

    @Test public void takingLessElementsThanContainedYieldsOnlyTheTakenAmount() {
        assertThat(on(1, 2, 3).take(2), contains(1, 2));
    }

    @Test public void takingElementsWhilePredicateIsTrue() {
        assertThat(on(1, 2, 3).takeWhile(equalOrLessThan(2)), contains(1, 2));
    }

    @Test public void takingElementsUntilPredicateIsTrue() {
        assertThat(on(1, 2, 4, 5).takeUntil(equalOrGreaterThan(3)), contains(1, 2));
    }
}
