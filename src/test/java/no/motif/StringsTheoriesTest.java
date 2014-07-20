package no.motif;

import static no.motif.Base.equalTo;
import static no.motif.Iterate.on;
import static no.motif.Singular.the;
import static no.motif.Strings.splittingOn;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.List;

import no.motif.f.Predicate.Always;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class StringsTheoriesTest {

    @Theory
    public void splitAnyStringOnNonExistingSubstringYieldsTheOriginalString(@ForAll String string, @ForAll String substring) {
        assumeThat(string, not(containsString(substring)));
        List<String> splitted = the(string).split(splittingOn(substring)).collect();
        assertThat(splitted, hasSize(1));
        assertThat(splitted, contains(string));
    }

    @Theory
    public void splitAnyStringOnNonExistingCharYieldsTheOriginalString(@ForAll String string, @ForAll char c) {
        assumeThat(string.indexOf(c), is(-1));
        List<String> splitted = the(string).split(splittingOn(c)).collect();
        assertThat(splitted, hasSize(1));
        assertThat(splitted, contains(string));
    }

    @Theory
    public void splittingStringsWhereEveryCharIsAnDelimiterYieldsEmptyStrings(@ForAll String string) {
        List<String> splitted = the(string).split(splittingOn(Always.yes())).collect();
        assertThat(splitted, hasSize(string.length() + 1));
        assertThat(splitted, everyItem(is("")));
    }

    @Theory
    public void splitsToOneMoreStringsThanOccurencesOfTheCharacter(@ForAll String string, @ForAll char c) {
        List<String> splitted = the(string).split(splittingOn(c)).collect();
        int charOccurences = on(string).filter(equalTo(c)).collect().size();
        assertThat(splitted, hasSize(charOccurences + 1));
    }
}
