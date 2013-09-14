package no.motif;

import static no.motif.Iterate.on;
import static no.motif.Reflect.getClass;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class ReflectTest {

    @Test
    @SuppressWarnings("unchecked")
    public void untypedClass() {
        assertThat(on("a", "b").map(getClass), contains((Class<?>)String.class, String.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void typedClass() {
        Class<? extends String> stringtype = String.class;
        assertThat(on("a", "b").map(Reflect.<String>type()), contains(stringtype, stringtype));
    }

    public static class A {}

    public static class B extends A {}

    public static class ThrowsUp { public ThrowsUp() throws Exception { throw new Exception("threw up"); }}

    @Test
    @SuppressWarnings("unchecked")
    public void createInstances() {
        assertThat(on(A.class, A.class).map(Reflect.<A>newInstance()), contains(isA(A.class), isA(A.class)));
    }

    @Test(expected = InstantiationException.class)
    public void createInstanceThrowsException() throws Throwable {
        try {
            Reflect.<Integer>newInstance().$(Integer.class);
        } catch (RuntimeException e) {
            throw e.getCause();
        }
    }


    @Test
    public void pickOutObjectsOfCertainSubtype() {
        List<A> onlyBs = on(new A(), new B()).filter(Reflect.isA(B.class)).collect();
        assertThat(onlyBs, hasSize(1));
        assertThat((B) onlyBs.get(0), isA(B.class));
    }
}
