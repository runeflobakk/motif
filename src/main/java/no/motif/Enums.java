package no.motif;

import static no.motif.Base.first;
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
    public static <E extends Enum<E>> Fn<String, E> to(final Class<E> enumType) { return new Fn<String, E>() {
        @Override public E $(String e) { return Enum.valueOf(enumType, e); }}; }


    /**
     * Convert from one enum to another by name.
     *
     * @param enumType The target enum type
     */
    public static <E extends Enum<E>> Fn<Enum<?>, E> toSameName(Class<E> enumType) {
        return first(name).then(to(enumType));
    }



    private Enums() {}

}
