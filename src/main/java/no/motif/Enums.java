package no.motif;

import static java.util.EnumSet.allOf;
import static no.motif.Base.first;
import static no.motif.Base.is;
import static no.motif.Iterate.on;
import no.motif.f.Fn;

public final class Enums {

    /**
     * Gets the {@link Enum#name() name} of enum constants.
     */
    public static final Fn<Enum<?>, String> name = new Fn<Enum<?>, String>() {
        @Override public String $(Enum<?> e) { return e.name(); }};


    /**
     * Resolve enums from constant names.
     *
     * @param enumType The enum type to resolve constants from.
     */
    public static <E extends Enum<E>> FromName<E> to(Class<E> enumType) { return new FromName<>(enumType); }


    /**
     * Convert from one enum to another by name.
     *
     * @param enumType The target enum type
     */
    public static <E extends Enum<E>> Fn<Enum<?>, E> toSameName(Class<E> enumType) {
        return first(name).then(to(enumType));
    }


    /**
     * {@link Fn} which resolves enum constants from names (<code>String</code>).
     * Use {@link #or(Enum)} to yield a fallback value for non-existing constants,
     * instead of throwing an exception.
     */
    public static final class FromName<E extends Enum<E>> implements Fn<String, E> {

        private final Class<E> enumType;

        private FromName(Class<E> enumType) {
            this.enumType = enumType;
        }

        @Override
        public E $(String name) {
            return Enum.valueOf(enumType, name);
        }

        public final Fn<String, E> or(final E fallback) {
            return new Fn<String, E>() { @Override public E $(String e) {
                return on(allOf(enumType)).map(name).filter(is(e)).map(to(enumType)).head().orElse(fallback);
            }};
        }
    }



    private Enums() {}

}
