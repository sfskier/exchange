package io.snapcard.exchange.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class USDJsonSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal number, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(number.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
}