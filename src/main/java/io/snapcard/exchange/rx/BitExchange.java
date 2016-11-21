package io.snapcard.exchange.rx;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public enum BitExchange {

    BITFINEX(BitfinexExchange.class.getName()),
    BITSTAMP(BitstampExchange.class.getName());

    private static final Logger LOGGER = LoggerFactory.getLogger(BitExchange.class);

    private final String exchangeName;
    private final Exchange exchange;

    BitExchange(String exchangeName) {
        this.exchangeName = exchangeName;
        this.exchange = ExchangeFactory.INSTANCE.createExchange(this.exchangeName);
    }

    public Ticker getTicker() {

        // 250-500ms
        Ticker ticker = null;
        try {
            ticker = exchange.getPollingMarketDataService().getTicker(CurrencyPair.BTC_USD);
        } catch (IOException e) {
            LOGGER.error("[Exception retrieving ticker]", e);
        }
        return ticker;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public Exchange getExchange() {
        return this.exchange;
    }
}
