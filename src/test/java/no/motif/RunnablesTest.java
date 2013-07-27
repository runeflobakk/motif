package no.motif;

import static no.motif.Base.first;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RunnablesTest {

    @Test public void chainOfRunnables() {

        class Inc implements Runnable {
            int num = 0;
            @Override public void run() { num++; }
        }

        Inc inc = new Inc();
        first(inc).then(NOP.runnable).then(inc).run();
        assertThat(inc.num, is(2));
    }
}
