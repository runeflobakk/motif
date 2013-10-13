package no.motif;

import static no.motif.Base.all;
import static no.motif.Base.cause;
import static no.motif.Base.exists;
import static no.motif.Base.extract;
import static no.motif.Base.isNull;
import static no.motif.Base.message;
import static no.motif.BaseTest.Phonenum.areaCode;
import static no.motif.BaseTest.Phonenum.number;
import static no.motif.Iterate.on;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.blank;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import no.motif.f.Fn;
import no.motif.f.Predicate.Always;

import org.junit.Test;

public class BaseTest {

    @Test
    public void existentialQuantifierFunction() {
        assertFalse(exists(Always.yes()).$(none()));

        assertTrue(exists(isNull).$(on(new Object(), null)));
        assertFalse(exists(blank).$(on("a", "b")));
    }

    @Test
    public void universalQuantifierFunction() {
        assertTrue(all(Always.no()).$(none()));

        assertTrue(all(isNull).$(on(null, null)));
        assertFalse(all(blank).$(on("a", "b")));
    }

    @Test
    public void getTheRootMessageOfAnException() {
        Exception e = new RuntimeException(new IllegalArgumentException(
                new UnsupportedOperationException("not supported")));
        assertThat(optional(e).map(Iterate.last(cause)).map(message).get(), is("not supported"));
        assertThat(optional(new RuntimeException("fail")).map(Iterate.last(cause)).map(message).get(), is("fail"));
        assertThat(optional(new Exception()).map(Iterate.last(cause)).map(message), is(Singular.<String>none()));
    }


    static class Phonenum { int _areaCode = 555; String _number = "12345";
        static final Fn<Phonenum, Integer> areaCode = new Fn<Phonenum, Integer>() {
            @Override public Integer $(Phonenum p) { return p._areaCode; }};

        static final Fn<Phonenum, String> number = new Fn<Phonenum, String>() {
            @Override public String $(Phonenum p) { return p._number; }};
    }


    @Test
    public void joinStringsFromTwoFns() {
        assertThat(optional(new Phonenum()).split(extract(areaCode, number)).join("-"), is("555-12345"));
    }

}
