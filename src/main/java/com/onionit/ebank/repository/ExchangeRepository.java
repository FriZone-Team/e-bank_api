package com.onionit.ebank.repository;

import com.onionit.ebank.model.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Page<Exchange> findByCardId(Long cardId, Pageable pageable);
}
