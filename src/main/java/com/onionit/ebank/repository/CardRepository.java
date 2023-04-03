package com.onionit.ebank.repository;

import com.onionit.ebank.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findByUserId(Long userId, Pageable pageable);
}
