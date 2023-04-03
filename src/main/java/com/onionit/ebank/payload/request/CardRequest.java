package com.onionit.ebank.payload.request;

import com.onionit.ebank.model.Exchange;
import com.onionit.ebank.model.user.User;
import com.onionit.ebank.payload.DateAuditPayload;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Data
public class CardRequest extends DateAuditPayload {

    private Long id;

    private String code;

    private String name;

    private long amount;

    private Instant expiredAt;

    private User user;

    private List<Exchange> exchanges;

    public List<Exchange> getExchanges() {

        return exchanges == null ? null : new ArrayList<>(exchanges);
    }

    public void setExchanges(List<Exchange> exchanges) {

        if (exchanges == null) {
            this.exchanges = null;
        } else {
            this.exchanges = Collections.unmodifiableList(exchanges);
        }
    }

    public void setRandomCode() {
        Random rand = new Random();
        setCode(String.format((Locale) null, "52%02d-%04d-%04d-%04d", rand.nextInt(100), rand.nextInt(10000), rand.nextInt(10000), rand.nextInt(10000)));
    }

    public void setExpiredIn(long time) {
        if (Objects.isNull(this.getCreatedAt())) {
            this.setCreatedAt(Instant.now());
        }
        setExpiredAt(this.getCreatedAt().plus(time, ChronoUnit.SECONDS));
    }

    public void setExpiredInDays(long days) {
        setExpiredIn(days * 24 * 60 * 60);
    }

    public void setExpiredInYears(long year) {
        setExpiredInDays(year * 365);
    }
}
