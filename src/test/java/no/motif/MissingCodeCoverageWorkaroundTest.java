package no.motif;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPrivate;
import static no.motif.Iterate.on;

import java.lang.reflect.Constructor;
import java.util.Set;

import no.motif.f.Predicate;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test to cope with certain idioms which makes it impossible to achieve 100%
 * test coverage. In particular, this includes
 */
public final class MissingCodeCoverageWorkaroundTest {

    private static final Logger LOG = LoggerFactory.getLogger(MissingCodeCoverageWorkaroundTest.class);

    private final Reflections motifPackage = new Reflections(Base.class.getPackage().getName(), new SubTypesScanner(false));
    @Test
    public void instantiateAllStaticUtilClassToAchieveCoverageForPrivateConstructors() throws Exception {
        Set<Class<?>> allTypes = motifPackage.getSubTypesOf(Object.class);
        for (Class<?> type : on(allTypes).filter(staticFinalUtilClass)) {
            LOG.info("Instantiating utility class " + type.getName());
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
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

}
