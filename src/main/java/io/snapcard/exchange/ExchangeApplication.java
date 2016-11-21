package io.snapcard.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.snapcard.exchange.mongo.BitTicker;
import io.snapcard.exchange.mongo.BitTickerRepository;
import io.snapcard.exchange.rx.BitExchange;
import io.snapcard.exchange.rx.OnSubscribeTickerTimer;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ExchangeApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeApplication.class);

    @Autowired
    private BitTickerRepository repository;

    public static final int TICKER_INTERVAL = 5;

    @Override
    public void run(String... args) throws Exception {

        List<Observable<Ticker>> observables = new ArrayList<>(BitExchange.values().length);
        for (BitExchange bitExchange : BitExchange.values()) {
            observables.add(Observable.create(new OnSubscribeTickerTimer(TICKER_INTERVAL, TimeUnit.SECONDS, bitExchange)));
        }

        Observable<Ticker> clock = Observable.merge(observables);

        clock.subscribe(ticker ->
                {
                    BitTicker bitTicker = new BitTicker(ticker);
                    repository.save(bitTicker);
                    try {
                        LOGGER.info("{} ", new ObjectMapper().writeValueAsString(bitTicker));
                    } catch (JsonProcessingException e) {
                        LOGGER.error("[Error serializing BitTicker.]", e);
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExchangeApplication.class, args);
    }
}
