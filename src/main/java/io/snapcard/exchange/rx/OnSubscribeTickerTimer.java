package io.snapcard.exchange.rx;

import org.knowm.xchange.dto.marketdata.Ticker;
import rx.Observable.OnSubscribe;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public final class OnSubscribeTickerTimer implements OnSubscribe<Ticker> {
    final long delay;
    final TimeUnit unit;
    final Scheduler scheduler;
    final BitExchange bitExchange;

    public OnSubscribeTickerTimer(long delay, TimeUnit unit, BitExchange bitExchange) {
        this.delay = delay;
        this.unit = unit;
        this.scheduler = Schedulers.computation();
        this.bitExchange = bitExchange;
    }

    @Override
    public void call(final Subscriber<? super Ticker> child) {
        final Worker worker = scheduler.createWorker();
        child.add(worker);
        worker.schedulePeriodically(() -> {
            Ticker ticker = bitExchange.getTicker();
            try {
                child.onNext(ticker);
            } catch (Throwable e) {
                try {
                    worker.unsubscribe();
                } finally {
                    Exceptions.throwOrReport(e, child);
                }
            }
        }, delay, delay, unit);
    }
}
