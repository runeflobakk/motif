package no.motif;

import static no.motif.Ints.dividedBy;
import static no.motif.Ints.doubled;
import static no.motif.Ints.increment;
import static no.motif.Ints.multipliedBy;
import static no.motif.Ints.multiply;
import static no.motif.Ints.subtract;
import static no.motif.Iterate.on;
import static no.motif.f.Apply.partially;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;


public class IntsTest {

	@Test
	public void powerFunctionHasBaseAs1stArgAndExponentAs2ndArg() {
		assertThat(Decimals.pow.$(3, 4), is(81d));
		assertThat(Decimals.pow.$(4, 3), is(64d));
	}

	@Test
	public void givenBitSizesYieldItsAmountOfPossibleDiscreetValues() {
	    assertThat(
	            on(1, 2, 4, 16, 32).map(partially(Decimals.pow).of(2)),
	            contains(2d, 4d, 16d, 65536d, 4294967296d));
    }

	@Test
	public void doublingValuesUsingPartiallyAppliedMultiplier() {
	    Matcher<Iterable<? extends Integer>> is4And10 = contains(4, 10);

        assertThat(on(2, 5).map(partially(multiply).of(2)), is4And10);
        assertThat(on(2, 5).map(partially(multiply).$(2)), is4And10);
        assertThat(on(2, 5).map(doubled), is4And10);
    }

	@Test
	public void intValueIsNullsafe() {
	    assertThat(Ints.intValue.$(null), nullValue());
    }


	@Test
    public void incrementInteger() {
        assertThat(increment.$(4), is(5));
    }

	@Test
    public void subtractValue() {
	    assertThat(on(4, 3).map(subtract(2)), contains(2, 1));
    }

	@Test
    public void multiplyValue() {
	    assertThat(on(4, 3).map(multipliedBy(3)), contains(12, 9));
    }

	@Test
    public void divideValue() {
	    assertThat(on(20, 13).map(dividedBy(4)), contains(5, 3));
    }
}
