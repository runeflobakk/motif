package no.motif;

import static no.motif.Base.is;
import static no.motif.Base.not;
import static no.motif.Base.where;
import static no.motif.Iterate.on;
import static no.motif.Reflect.annotatedWith;
import static no.motif.Reflect.className;
import static no.motif.Reflect.getClass;
import static no.motif.Reflect.name;
import static no.motif.Reflect.packageName;
import static no.motif.Reflect.simpleName;
import static no.motif.Singular.the;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

    @Retention(RetentionPolicy.RUNTIME) @interface Awesome {}

    public static class A {}

    @Awesome
    public static class B extends A {
        public int num1, num2;
    }

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


    @Test
    public void getNameOfFields() {
        assertThat(on(B.class.getDeclaredFields()).map(name).filter(not(is("$jacocoData"))), contains("num1", "num2"));
    }

    @Test
    public void getAnnotatedObjects() {
        A a = new A();
        B b = new B();
        assertThat(on(a, b).filter(where(getClass, annotatedWith(Awesome.class))), contains((A) b));
    }

    @Test
    public void getNameOfClasses() {
        assertThat(on(String.class, Serializable.class).map(className), contains("java.lang.String", "java.io.Serializable"));
        assertThat(on(String.class, Serializable.class).map(simpleName), contains("String", "Serializable"));
    }

    @Test
    public void getPackageOfClasses() {
        assertThat(the(String.class).map(packageName), contains("java.lang"));
    }


}
