package no.motif;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPrivate;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.util.Set;

import no.motif.f.Fn;
import no.motif.f.Predicate;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test to cope with certain idioms which makes it impossible to achieve 100%
 * test coverage.
 */
public final class CoverageWorkaroundTest {

    private static final Logger LOG = LoggerFactory.getLogger(CoverageWorkaroundTest.class);

    private final Reflections motifPackage = new Reflections(Base.class.getPackage().getName(), new SubTypesScanner(false));
    @Test
    public void instantiateAllStaticUtilClassToAchieveCoverageForPrivateConstructors() throws Exception {
        Set<Class<?>> allTypes = motifPackage.getSubTypesOf(Object.class);
        for (Constructor<?> constructor : on(allTypes).filter(staticFinalUtilClass).map(declaredConstructor())) {
            LOG.info("Instantiating " + constructor.getName());
            assertThat(constructor.newInstance(), not(nullValue()));
        }
    }


    static Predicate<Class<?>> staticFinalUtilClass = new Predicate<Class<?>>() {
        @Override
        public boolean $(Class<?> type) {
            if (isFinal(type.getModifiers()) && type.getSuperclass() == Object.class) {
                Constructor<?>[] constructors = type.getDeclaredConstructors();
                if (constructors.length == 1 && constructors[0].getParameterTypes().length == 0) {
                    return isPrivate(constructors[0].getModifiers());
                }
            }
            return false;
        }
    };

    static Fn<Class<?>, Constructor<?>> declaredConstructor(final Class<?> ... parameterTypes) {
        return new Fn<Class<?>, Constructor<?>>() {
            @Override
            public Constructor<?> $(Class<?> type) {
                try {
                    Constructor<?> constructor = type.getDeclaredConstructor(parameterTypes);
                    constructor.setAccessible(true);
                    return constructor;
                } catch (NoSuchMethodException | SecurityException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };
    }

}
