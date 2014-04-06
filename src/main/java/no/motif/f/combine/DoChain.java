package no.motif.f.combine;

import no.motif.f.Do;

public class DoChain<T> implements Do<T> {

    private final Do<T> firstAction;
    private final Do<? super T> secondAction;

    public DoChain(Do<T> firstAction, Do<? super T> secondAction) {
        this.firstAction = firstAction;
        this.secondAction = secondAction;
    }

    public DoChain<T> then(Do<? super T> nextAction) {
        return new DoChain<>(this, nextAction);
    }

    @Override
    public void $(T value) {
        firstAction.$(value);
        secondAction.$(value);
    }

}
