package com.onionit.ebank.service;

import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.ExchangeRequest;
import com.onionit.ebank.payload.ExchangeResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.UserPrincipal;

public interface ExchangeService {

    PagedResponse<ExchangeResponse> getAllExchanges(int page, int size);

    ExchangeResponse getExchange(Long id);

    ExchangeResponse updateExchange(Long id, ExchangeRequest exchangeRequest, UserPrincipal currentUser);

    ExchangeResponse addExchange(ExchangeRequest exchangeRequest, UserPrincipal currentUser);

    ApiResponse deleteExchange(Long id, UserPrincipal currentUser);

    PagedResponse<ExchangeResponse> getAllExchangeByCard(Long albumId, int page, int size);

}