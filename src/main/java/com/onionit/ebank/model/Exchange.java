package com.onionit.ebank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onionit.ebank.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "exchanges")
public class Exchange extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @NotBlank
    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public Exchange(@NotBlank Long amount, @NotBlank String message, Card card) {
        this.amount = amount;
        this.message = message;
        this.card = card;
    }

    @JsonIgnore
    public Card getCard() {
        return card;
    }
}
