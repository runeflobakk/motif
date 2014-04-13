package no.motif;

import static no.motif.Iterate.on;
import static no.motif.Strings.after;
import static no.motif.Strings.afterLast;
import static no.motif.Strings.allBetween;
import static no.motif.Strings.alphabetic;
import static no.motif.Strings.alphanumeric;
import static no.motif.Strings.append;
import static no.motif.Strings.before;
import static no.motif.Strings.beforeLast;
import static no.motif.Strings.between;
import static no.motif.Strings.betweenOuter;
import static no.motif.Strings.blank;
import static no.motif.Strings.bytes;
import static no.motif.Strings.concat;
import static no.motif.Strings.contains;
import static no.motif.Strings.endsWith;
import static no.motif.Strings.first;
import static no.motif.Strings.from;
import static no.motif.Strings.hasLength;
import static no.motif.Strings.inBetween;
import static no.motif.Strings.indexOf;
import static no.motif.Strings.last;
import static no.motif.Strings.lastIndexOf;
import static no.motif.Strings.length;
import static no.motif.Strings.lowerCased;
import static no.motif.Strings.matches;
import static no.motif.Strings.numeric;
import static no.motif.Strings.prepend;
import static no.motif.Strings.repeat;
import static no.motif.Strings.reversed;
import static no.motif.Strings.startsWith;
import static no.motif.Strings.substring;
import static no.motif.Strings.toDouble;
import static no.motif.Strings.toInt;
import static no.motif.Strings.toLong;
import static no.motif.Strings.trimmed;
import static no.motif.Strings.upperCased;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
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
    public void convertToLong() {
        assertThat(toLong.$("42"), is(42L));
        assertThat(toLong.$("-42"), is(-42L));
        assertThat(toLong.$(null), is(0L));
    }

    @Test
    public void convertToDouble() {
        assertThat(toDouble.$("42"), is(42.0));
        assertThat(toDouble.$("-3.14"), is(-3.14));
        assertThat(toDouble.$(null), is(0.0));
    }

    @Test
    public void containsSequenceOfChars() {
        assertThat(Strings.contains(null).$(null), is(false));
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
    public void substringByStartAndEndIndex() {
        assertThat(substring(1, 3).$("abcd"), is("bc"));
        assertThat(substring(1, 3).$(null), nullValue());
        assertThat(substring(4, 12).$(""), is(""));
        assertThat(substring(4, 12).$("abcde"), is("e"));
        assertThat(substring(0, 1).$("ab"), is("a"));
        assertThat(substring(4, 1).$("ab"), is(""));
        assertThat(substring(1, 1).$("ab"), is(""));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void substringWithNegativeStartIndexIsAnError() {
        substring(-1, 1).$("ab");
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void substringWithNegativeEndIndexIsAnError() {
        substring(0, -1).$("ab");
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

    @Test
    public void repeatedString() {
        assertThat(repeat(0).$("something"), is(""));
        assertThat(repeat(1).$("xx"), is("xx"));
        assertThat(repeat(2).$("xx"), is("xxxx"));
        assertThat(repeat(2).$(null), nullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void repeatingNegativeAmountIsNotValid() {
        repeat(-1).$("");
    }

    @Test
    public void repeatedStringWithDelimiter() {
        assertThat(repeat(0, ", ").$("something"), is(""));
        assertThat(repeat(1, ", ").$("xx"), is("xx"));
        assertThat(repeat(4, ".").$("0"), is("0.0.0.0"));
        assertThat(repeat(4, ".").$(null), nullValue());
    }

    @Test
    public void bytesOfAString() {
        assertThat(bytes.$("abc"), is("abc".getBytes()));
        assertThat(bytes.$(null).length, is(0));
    }


    @Test
    public void extractSubstringAfterFirstOccurenceOfGivenString() {
        assertThat(after("anything").$(null), nullValue());
        assertThat(after("anything").$(""), nullValue());
        assertThat(after((String) null).$("anything"), is("anything"));
        assertThat(after("").$("anything"), is("anything"));
        assertThat(after("").$(null), nullValue());
        assertThat(after("b").$("abc"), is("c"));
        assertThat(after("c").$("abc"), is(""));
        assertThat(after("d").$("abc"), nullValue());
        assertThat(after("cd").$("abcdcbabcdc"), is("cbabcdc"));
    }

    @Test
    public void extractSubstringBeforeFirstOccurrenceOfGivenString() {
        assertThat(before("anything").$(null), nullValue());
        assertThat(before("x").$(""), nullValue());
        assertThat(before((String) null).$("anything"), is("anything"));
        assertThat(before("").$("anything"), is(""));
        assertThat(before("").$(null), nullValue());
        assertThat(before("a").$("abc"), is(""));
        assertThat(before("b").$("abc"), is("a"));
        assertThat(before("c").$("abc"), is("ab"));
        assertThat(before("d").$("abc"), nullValue());
        assertThat(before("cd").$("abcdcbabcdc"), is("ab"));
    }

    @Test
    public void extractSubstringAfterLastOccurenceOfGivenString() {
        assertThat(afterLast("cd").$("abcdcbabcdc"), is("c"));
        assertThat(afterLast("c").$("abcdcbabcdc"), is(""));
        assertThat(afterLast("x").$("abcdcbabcdc"), nullValue());
        assertThat(afterLast("").$("abcdcbabcdc"), is(""));
        assertThat(afterLast(null).$("abcdcbabcdc"), is(""));
        assertThat(afterLast("x").$(null), nullValue());
    }

    @Test
    public void extractSubstringBeforeLastOccurrenceOfGivenString() {
        assertThat(beforeLast("anything").$(null), nullValue());
        assertThat(beforeLast("").$(null), nullValue());
        assertThat(beforeLast("").$("anything"), is("anything"));
        assertThat(beforeLast("anything").$(""), nullValue());
        assertThat(beforeLast(null).$("anything"), is("anything"));
        assertThat(beforeLast("a").$("abc"), is(""));
        assertThat(beforeLast("b").$("abc"), is("a"));
        assertThat(beforeLast("c").$("abc"), is("ab"));
        assertThat(beforeLast("d").$("abc"), nullValue());
        assertThat(beforeLast("cd").$("abcdcda"), is("abcd"));
    }

    @Test
    public void extractSubstringBeforeIndexOutOfBoundsYieldsTheOriginalString() {
        assertThat(before(99).$("abc"), is("abc"));
        assertThat(before(3).$("abc"), is("abc"));
    }

    @Test
    public void extractSubstringAfterIndexOutOfBoundsYieldsTheEmptyString() {
        assertThat(from(99).$("abc"), is(""));
        assertThat(from(3).$("abc"), is(""));
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void extractSubstringBeforeNegativeIndexIsInvalid() {
        before(-1).$("x");
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void extractSubstringAfterIndexValueOfMinus2OrLessIsInvalid() {
        from(-1).$("x");
    }

    @Test
    public void extractSubstringAfterIndexValueOfMinus1YieldsOriginalString() {
        assertThat(from(0).$("xyz"), is("xyz"));
    }


    @Test
    public void extractStringBetweenTwoStrings() {
        assertThat(between("a", "b").$("ab"), is(""));
        assertThat(between("a", "b").$("abb"), is(""));
        assertThat(between("a", "b").$("aabb"), is("a"));
        assertThat(between("b", "d").$("abcde"), is("c"));
        assertThat(between("a", "b").$(null), nullValue());
        assertThat(between(null, "b").$("ab"), nullValue());
        assertThat(between("a", null).$("ab"), nullValue());
        assertThat(between("c", "b").$("abcdcba"), is("dc"));
        assertThat(between("a", "").$("ab"), is(""));
        assertThat(between("", "b").$("ab"), is("a"));
        assertThat(between("", "").$(""), is(""));
        assertThat(between("", "").$("abc"), is(""));
        assertThat(between("", "x").$(""), nullValue());
        assertThat(between("", "x").$("a"), nullValue());
        assertThat(between("a", "x").$("a"), nullValue());
        assertThat(between("a", "").$("a"), is(""));
    }

    @Test
    public void extractStringBetweenTwoOutermostStrings() {
        assertThat(betweenOuter("a", "b").$("ab"), is(""));
        assertThat(betweenOuter("a", "b").$("abb"), is("b"));
        assertThat(betweenOuter("a", "b").$("aabb"), is("ab"));
        assertThat(betweenOuter("b", "d").$("abcdde"), is("cd"));
        assertThat(betweenOuter("a", "b").$(null), nullValue());
        assertThat(betweenOuter(null, "b").$("ab"), nullValue());
        assertThat(betweenOuter("a", null).$("ab"), nullValue());
    }

    @Test
    public void extractAllStringsOccurringBetweenGivenStrings() {
        assertThat(allBetween(null, null).$("<td>1</td> <td>2</td> <td>3</td>"), emptyIterable());
        assertThat(allBetween(null, ")").$("(1) (2) (3)"), emptyIterable());
        assertThat(allBetween("(", null).$("(1) (2) (3)"), emptyIterable());
        assertThat(allBetween("<td>", "</td>").$("<td>1</td> <td>2</td> <td>3</td>"), contains("1", "2", "3"));
        assertThat(allBetween("(", ")").$("(1) (2) (3)"), contains("1", "2", "3"));
        assertThat(allBetween("(", ") ").$("(1) (2) (3)"), contains("1", "2"));
        assertThat(allBetween("(", "]").$("(1) (2)"), emptyIterable());
        assertThat(allBetween("", ")").$("(1) (2)"), contains("(1", " (2"));
        assertThat(allBetween("(", "").$("(1) (2)"), contains("", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void extractAllBetweenTwoEmptyStringsIsAnError() {
        allBetween("", "").$("x");
    }

    @Test
    public void indexOfFirstOccurrenceOfCharacter() {
        assertThat(indexOf('b').$("ab"), is(1));
        assertThat(indexOf('c').$("ab"), nullValue());
        assertThat(indexOf(null).$("anything"), nullValue());
    }

    @Test
    public void indexOfLastOccurrenceOfCharacter() {
        assertThat(lastIndexOf('b').$("abb"), is(2));
        assertThat(lastIndexOf('c').$("ab"), nullValue());
        assertThat(lastIndexOf(null).$("anything"), nullValue());
    }

    @Test
    public void indexOfFirstOccurrenceOfSubstring() {
        assertThat(indexOf("bc").$("abcabc"), is(1));
        assertThat(indexOf("bcd").$("abcabc"), nullValue());
    }
}
