package com.onionit.ebank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onionit.ebank.model.audit.DateAudit;
import com.onionit.ebank.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "cards", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class Card extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "code")
    private String code;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "amount")
    private long amount;

    @Column(nullable = false, updatable = false)
    private Instant expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exchange> exchanges;

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public List<Exchange> getExchanges() {
        return this.exchanges == null ? null : new ArrayList<>(this.exchanges);
    }

    public void setExchanges(List<Exchange> exchange) {
        if (exchange == null) {
            this.exchanges = null;
        } else {
            this.exchanges = Collections.unmodifiableList(exchange);
        }
    }
}
