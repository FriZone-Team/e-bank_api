package com.onionit.ebank.service;

import com.onionit.ebank.model.Card;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.CardResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.payload.request.CardRequest;
import com.onionit.ebank.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface CardService {

    PagedResponse<CardResponse> getAllCard(int page, int size);

    ResponseEntity<Card> addCard(CardRequest cardRequest, UserPrincipal currentUser);

    ResponseEntity<Card> getCard(Long id);

    ResponseEntity<CardResponse> updateCard(Long id, CardRequest newAlbum, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteCard(Long id, UserPrincipal currentUser);

    PagedResponse<Card> getUserCards(String username, int page, int size);

}
