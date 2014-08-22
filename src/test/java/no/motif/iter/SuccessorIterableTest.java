package no.motif.iter;

import static no.motif.Exceptions.cause;
import static no.motif.Ints.increment;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.hamcrest.Matchers;
import org.junit.Test;

public class SuccessorIterableTest {

    @Test
    public void getsSuccessors() {
        Iterator<Integer> naturalNums = new SuccessorIterable<>(0, increment).iterator();
        assertThat(naturalNums.hasNext(), is(true));
        assertThat(naturalNums.next(), is(0));
        assertThat(naturalNums.next(), is(1));
        assertThat(naturalNums.next(), is(2));
    }

    @Test
    public void endsWhenSuccessorFunctionYieldsNull() {
        RuntimeException theCause = new RuntimeException();
        Exception exception = new Exception(theCause);
        Iterable<Throwable> allFails = new SuccessorIterable<>(exception, cause);
        assertThat(allFails, Matchers.<Throwable>contains(exception, theCause));
    }
}
