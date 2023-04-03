package com.onionit.ebank.controller;

import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.ExchangeRequest;
import com.onionit.ebank.payload.ExchangeResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.CurrentUser;
import com.onionit.ebank.security.UserPrincipal;
import com.onionit.ebank.service.ExchangeService;
import com.onionit.ebank.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @GetMapping
    public PagedResponse<ExchangeResponse> getAllExchanges(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return exchangeService.getAllExchanges(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ExchangeResponse> addExchange(@Valid @RequestBody ExchangeRequest exchangeRequest,
                                                        @CurrentUser UserPrincipal currentUser) {
        ExchangeResponse exchangeResponse = exchangeService.addExchange(exchangeRequest, currentUser);

        return new ResponseEntity<>(exchangeResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeResponse> getExchange(@PathVariable(name = "id") Long id) {
        ExchangeResponse exchangeResponse = exchangeService.getExchange(id);

        return new ResponseEntity<>(exchangeResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ExchangeResponse> updateExchange(@PathVariable(name = "id") Long id,
                                                           @Valid @RequestBody ExchangeRequest exchangeRequest, @CurrentUser UserPrincipal currentUser) {

        ExchangeResponse exchangeResponse = exchangeService.updateExchange(id, exchangeRequest, currentUser);

        return new ResponseEntity<>(exchangeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteExchange(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = exchangeService.deleteExchange(id, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
