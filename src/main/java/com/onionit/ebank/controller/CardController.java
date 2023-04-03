package com.onionit.ebank.controller;

import com.onionit.ebank.exception.ResponseEntityErrorException;
import com.onionit.ebank.model.Card;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.CardResponse;
import com.onionit.ebank.payload.ExchangeResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.payload.request.CardRequest;
import com.onionit.ebank.security.CurrentUser;
import com.onionit.ebank.security.UserPrincipal;
import com.onionit.ebank.service.CardService;
import com.onionit.ebank.service.ExchangeService;
import com.onionit.ebank.utils.AppConstants;
import com.onionit.ebank.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ExchangeService exchangeService;

    @ExceptionHandler(ResponseEntityErrorException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityErrorException exception) {
        return exception.getApiResponse();
    }

    @GetMapping
    public PagedResponse<CardResponse> getAllCards(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        AppUtils.validatePageNumberAndSize(page, size);

        return cardService.getAllCard(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Card> addCard(@Valid @RequestBody CardRequest cardRequest, @CurrentUser UserPrincipal currentUser) {
        return cardService.addCard(cardRequest, currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable(name = "id") Long id) {
        return cardService.getCard(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CardResponse> updateCard(@PathVariable(name = "id") Long id, @Valid @RequestBody CardRequest newCard,
                                                   @CurrentUser UserPrincipal currentUser) {
        return cardService.updateCard(id, newCard, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCard(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return cardService.deleteCard(id, currentUser);
    }

    @GetMapping("/{id}/exchange")
    public ResponseEntity<PagedResponse<ExchangeResponse>> getAllExchangeByCard(@PathVariable(name = "id") Long id,
                                                                                @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                                @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<ExchangeResponse> response = exchangeService.getAllExchangeByCard(id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
