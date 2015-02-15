package no.motif.maps;

import no.motif.f.Fn;

public class NotPossibleOnMapViewOfFn extends UnsupportedOperationException {
    NotPossibleOnMapViewOfFn(Fn<?, ?> fn, String description) {
        super("As this is a map view of a " + Fn.class.getName() + " (" + fn.getClass().getName() + "), " + description);
    }
}