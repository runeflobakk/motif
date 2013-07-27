package no.motif.f.combine;


public class RunnableChain implements Runnable {

    private final Runnable first;
    private final Runnable second;

    public RunnableChain(Runnable first, Runnable second) {
        this.first = first;
        this.second = second;
    }

    public RunnableChain then(Runnable next) {
        return new RunnableChain(this, next);
    }

    @Override public void run() {
        first.run();
        second.run();
    }

}
