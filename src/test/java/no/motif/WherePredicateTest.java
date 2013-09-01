package no.motif;

import static no.motif.Base.equalTo;
import static no.motif.Base.where;
import static no.motif.Iterate.on;
import static no.motif.Strings.lowerCased;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import no.motif.f.Fn;
import no.motif.types.Elements;

import org.hamcrest.Matchers;
import org.junit.Test;

public class WherePredicateTest {

    @Test
    public void combineFnAndPredicate() {
        assertTrue(where(lowerCased, equalTo("ab")).$("Ab"));
        assertFalse(where(lowerCased, equalTo("ab")).$(null));
    }

    static class A {
        String x;
        static final Fn<A, String> X = new Fn<A, String>() {
            @Override public String $(A a) {return a.x;};};
    }

    @Test
    public void filterDomainObjectsUsingWherePredicate() {
        Elements<A> onlyXEqualTo2 = on(new A(){{x = "1";}}, new A(){{x = "2";}}).filter(where(A.X, equalTo("2")));
        assertThat(onlyXEqualTo2, Matchers.<A>iterableWithSize(1));
        Iterator<A> iterator = onlyXEqualTo2.iterator();
        A head = iterator.next();
        assertThat(head.x, is("2"));
    }

}
