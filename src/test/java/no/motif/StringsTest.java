package no.motif;

import static no.motif.Iterate.on;
import static no.motif.Strings.alphabetic;
import static no.motif.Strings.alphanumeric;
import static no.motif.Strings.append;
import static no.motif.Strings.inBetween;
import static no.motif.Strings.blank;
import static no.motif.Strings.concat;
import static no.motif.Strings.contains;
import static no.motif.Strings.endsWith;
import static no.motif.Strings.first;
import static no.motif.Strings.hasLength;
import static no.motif.Strings.last;
import static no.motif.Strings.length;
import static no.motif.Strings.lowerCased;
import static no.motif.Strings.matches;
import static no.motif.Strings.numeric;
import static no.motif.Strings.prepend;
import static no.motif.Strings.reversed;
import static no.motif.Strings.startsWith;
import static no.motif.Strings.toDouble;
import static no.motif.Strings.toInt;
import static no.motif.Strings.trimmed;
import static no.motif.Strings.upperCased;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringsTest {

    @Test
    public void toUpperCase() {
        assertThat(upperCased.$("abc"), is("ABC"));
    }

    @Test
    public void toLowerCase() {
        assertThat(lowerCased.$("ABC"), is("abc"));
    }

    @Test
    public void trimmed() {
        assertThat(trimmed.$("  x y  "), is("x y"));
    }

    @Test
    public void blank() {
        assertThat(blank.$("x"), is(false));
        assertThat(blank.$(""), is(true));
        assertThat(blank.$(" "), is(true));
        assertThat(blank.$("\n \t "), is(true));
        assertThat(blank.$(null), is(true));
    }

    @Test
    public void numeric() {
        assertThat(numeric.$("1"), is(true));
        assertThat(numeric.$("1234567890"), is(true));

        assertThat(numeric.$("123x"), is(false));
        assertThat(numeric.$(""), is(false));
        assertThat(numeric.$(null), is(false));
    }

    @Test
    public void length() {
        assertThat(length.$(""), is(0));
        assertThat(length.$("xyz"), is(3));
        assertThat(length.$(null), is(0));
    }

    @Test
    public void convertToInt() {
        assertThat(toInt.$("42"), is(42));
        assertThat(toInt.$("-42"), is(-42));
        assertThat(toInt.$(null), is(0));
    }

    @Test
    public void convertToDouble() {
        assertThat(toDouble.$("42"), is(42.0));
        assertThat(toDouble.$("-3.14"), is(-3.14));
        assertThat(toDouble.$(null), is(0.0));
    }

    @Test
    public void containsSequenceOfChars() {
        assertThat(contains(null).$(null), is(false));
        assertThat(contains("").$(null), is(false));
        assertThat(contains("").$(""), is(true));
        assertThat(contains("").$("a"), is(true));
        assertThat(contains("b").$("a"), is(false));
        assertThat(contains("b").$("abc"), is(true));
    }

    @Test
    public void startsWithPrefix() {
        assertThat(startsWith(null).$(null), is(false));
        assertThat(startsWith(null).$("x"), is(false));
        assertThat(startsWith("").$(null), is(false));
        assertThat(startsWith("b").$("ab"), is(false));
        assertThat(startsWith("a").$("a"), is(true));
        assertThat(startsWith("a").$("ab"), is(true));
    }

    @Test
    public void endsWithSuffix() {
        assertThat(endsWith(null).$(null), is(false));
        assertThat(endsWith(null).$(""), is(false));
        assertThat(endsWith("").$(null), is(false));
        assertThat(endsWith("x").$(null), is(false));
        assertThat(endsWith("bc").$("abc"), is(true));
    }

    @Test
    public void regexMatches() {
        assertThat(matches(null).$(null), is(false));
        assertThat(matches("x").$("x"), is(true));
        assertThat(matches(".+x.+").$("asdfxasdf"), is(true));
    }


    @Test
    public void alphanumeric() {
        assertTrue(alphanumeric.$("123456789"));
        assertTrue(alphanumeric.$("abcæøå"));
        assertTrue(alphanumeric.$("1a"));
        assertFalse(alphanumeric.$(""));
        assertFalse(alphanumeric.$("  "));
        assertFalse(alphanumeric.$("a1 "));
        assertFalse(alphanumeric.$("#$"));
    }

    @Test
    public void alphabetic() {
        assertTrue(alphabetic.$("abcæøå"));
        assertFalse(alphabetic.$(""));
        assertFalse(alphabetic.$("  "));
        assertFalse(alphabetic.$("  a"));
        assertFalse(alphabetic.$("#$"));
    }

    @Test
    public void lengthOfString() {
        assertTrue(hasLength(4).$("abcd"));
        assertFalse(hasLength(4).$("abc"));
        assertTrue(hasLength(0).$(null));
        assertFalse(hasLength(1).$(null));
    }


    @Test
    public void concatenating() {
        assertThat(concat.$(1, 2), is("12"));
    }

    @Test
    public void concatNullIsEmptyString() {
        assertThat(concat.$(null, "a"), is("a"));
        assertThat(concat.$("a", null), is("a"));
        assertThat(concat.$(null, null), is(""));
    }

    @Test
    public void appending() {
        assertThat(append("y").$("x"), is("xy"));
    }

    @Test
    public void prepending() {
        assertThat(prepend("x").$("y"), is("xy"));
    }

    @Test
    public void substring() {
        assertThat(Strings.substring(1, 3).$("abcd"), is("bc"));
    }

    @Test
    public void leadingCharacters() {
        assertThat(first(3).$("abcd"), is("abc"));
        assertThat(first(3).$(null), is(""));
        assertThat(first(3).$("ab"), is("ab"));
        assertThat(first(0).$("ab"), is(""));
    }

    @Test
    public void trailingCharacters() {
        assertThat(last(3).$(null), is(""));
        assertThat(last(3).$("abc"), is("abc"));
        assertThat(last(3).$("abcd"), is("bcd"));
        assertThat(last(3).$("ab"), is("ab"));
        assertThat(last(0).$("ab"), is(""));
    }

    @Test
    public void join() {
        assertThat(on("a", "b", 1).join(), is("ab1"));
        assertThat(on().join(), is(""));
    }

    @Test
    public void joinedWithSeparator() {
        assertThat(on("a", "b", 1).join(", "), is("a, b, 1"));
        assertThat(on("a").join(", "), is("a"));
        assertThat(on().join(", "), is(""));
    }

    @Test
    public void insertStringBetweenTwoStrings() {
        assertThat(on("a", "b").map(inBetween("<td>", "</td>")).join(), is("<td>a</td><td>b</td>"));
    }

    @Test
    public void reversedString() {
        assertThat(reversed.$(null), nullValue());
        assertThat(reversed.$("a"), is("a"));
        assertThat(reversed.$("abc"), is("cba"));
    }


}
