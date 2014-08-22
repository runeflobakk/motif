package no.motif;

import static no.motif.Base.all;
import static no.motif.Base.always;
import static no.motif.Base.alwaysThrow;
import static no.motif.Base.exists;
import static no.motif.Base.extract;
import static no.motif.Base.first;
import static no.motif.Base.isNull;
import static no.motif.Base.not;
import static no.motif.Base.notNull;
import static no.motif.Base.when;
import static no.motif.BaseTest.Phonenum.areaCode;
import static no.motif.BaseTest.Phonenum.number;
import static no.motif.Iterate.on;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.append;
import static no.motif.Strings.blank;
import static no.motif.Strings.endsWith;
import static no.motif.Strings.indexOf;
import static no.motif.Strings.lowerCased;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import no.motif.f.Fn;
import no.motif.f.Predicate.Always;
import no.motif.f.combine.When;

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
    public void alwaysYieldConstantValueForDifferentFunctionTypes() {
        assertThat(on("a", "b").map(always(3)), contains(3, 3));
        assertThat(on("a", "b").reduce(0, always(42)), is(42));
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

    @Test
    public void guardFnWithPredicate() {
        When<Object, Object> throwWhenNull = when(isNull, alwaysThrow(new RuntimeException()));
        assertThat(throwWhenNull.$("blocked"), nullValue());
        assertThat(throwWhenNull.orElse("passed").$("blocked"), is((Object) "passed"));
        assertThat(when(notNull, lowerCased).$("A"), is("a"));
        assertThat(
                first((Fn<Object, Object>)always(null))
                .then(when(notNull, always("not expected")).orElse("expected")).$("anything"),
            is("expected"));

        Fn<String, String> appendSlashIfMissing = when(not(endsWith("/")), append("/")).orElse(NOP.<String>fn());
        assertThat(appendSlashIfMissing.$("x"), is("x/"));
        assertThat(appendSlashIfMissing.$(""), is("/"));
        assertThat(appendSlashIfMissing.$("x/"), is("x/"));
        assertThat(appendSlashIfMissing.$(null), is("/"));

        assertThat(when(notNull, indexOf('b')).$("ab"), is(1));
        assertThat(when(notNull, indexOf('b')).orElse(-1).$(null), is(-1));
    }


}
