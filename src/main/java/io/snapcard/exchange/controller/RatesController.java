package io.snapcard.exchange.controller;

import io.snapcard.exchange.mongo.BitTicker;
import io.snapcard.exchange.mongo.BitTickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static io.snapcard.exchange.ExchangeApplication.TICKER_INTERVAL;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rates")
public class RatesController {

    private static Logger LOGGER = LoggerFactory.getLogger(RatesController.class);

    @Autowired
    private BitTickerRepository repository;

    @RequestMapping("")
    BitTicker rates() {
        LOGGER.info("/rates endpoint request");
        return repository.findFirstByOrderByCreatedDesc();
    }

    @RequestMapping(value = "/historical/{time}")
    BitTicker historical(@PathVariable long time) {
        LOGGER.info("/rates/historical/" + time + " endpoint request");
        // https://currentmillis.com/
        Date from = new Date(time - TICKER_INTERVAL * 500);
        Date to = new Date(time + TICKER_INTERVAL * 500);
        return repository.findLastByCreatedBetween(from, to);
    }
}