package com.onionit.ebank.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.onionit.ebank.model.Exchange;
import com.onionit.ebank.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(Include.NON_NULL)
public class CardResponse extends DateAuditPayload {
    private Long id;

    private String code;

    private String name;

    private Instant expiredAt;

    @JsonIgnore
    private User user;

    private List<Exchange> exchange;

    public List<Exchange> getExchange() {

        return exchange == null ? null : new ArrayList<>(exchange);
    }

    public void setExchange(List<Exchange> exchange) {

        if (exchange == null) {
            this.exchange = null;
        } else {
            this.exchange = Collections.unmodifiableList(exchange);
        }
    }
}
