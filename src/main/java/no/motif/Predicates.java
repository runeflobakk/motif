package no.motif;

import java.util.Objects;

import no.motif.f.Predicate;

public final class Predicates {

    public static <T> Predicate<T> equalTo(final T value) {
        return new Predicate<T>() { @Override public boolean $(T input) { return Objects.equals(input, value); } };
    }


    private Predicates() {} static { new Predicates(); }

}
