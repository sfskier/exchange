package io.snapcard.exchange.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface BitTickerRepository extends MongoRepository<BitTicker, String> {

    public BitTicker findLastByCreatedBetween(Date from, Date to);

    public BitTicker findFirstByOrderByCreatedDesc();
}
