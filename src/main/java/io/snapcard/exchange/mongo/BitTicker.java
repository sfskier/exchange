package io.snapcard.exchange.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.snapcard.exchange.json.USDJsonSerializer;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "bit_ticker")
@JsonPropertyOrder({"bid", "ask", "last", "created"})
public class BitTicker {

    @JsonIgnore
    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Indexed
    private java.util.Date created;
    @JsonSerialize(using = USDJsonSerializer.class)
    private BigDecimal bid;
    @JsonSerialize(using = USDJsonSerializer.class)
    private BigDecimal ask;
    @JsonSerialize(using = USDJsonSerializer.class)
    private BigDecimal last;

    public BitTicker() {
    }

    public BitTicker(Ticker ticker) {
        this.last = ticker.getLast();
        this.bid = ticker.getBid();
        this.ask = ticker.getAsk();
        this.created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getLast() {
        return last;
    }

    @Override
    public String toString() {
        return String.format(
                "BitTicker[id=%s, created='%s', bid='%s', ask='%s', last='%s']",
                id, created, bid, ask, last);
    }
}
