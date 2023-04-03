package com.onionit.ebank.service.impl;

import com.onionit.ebank.exception.ResourceNotFoundException;
import com.onionit.ebank.exception.UnauthorizedException;
import com.onionit.ebank.model.Card;
import com.onionit.ebank.model.Exchange;
import com.onionit.ebank.model.role.RoleName;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.ExchangeRequest;
import com.onionit.ebank.payload.ExchangeResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.repository.CardRepository;
import com.onionit.ebank.repository.ExchangeRepository;
import com.onionit.ebank.security.UserPrincipal;
import com.onionit.ebank.service.ExchangeService;
import com.onionit.ebank.utils.AppConstants;
import com.onionit.ebank.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.onionit.ebank.utils.AppConstants.*;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public PagedResponse<ExchangeResponse> getAllExchanges(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Exchange> exchanges = exchangeRepository.findAll(pageable);

        List<ExchangeResponse> exchangeRespons = new ArrayList<>(exchanges.getContent().size());
        for (Exchange exchange : exchanges.getContent()) {
            exchangeRespons.add(new ExchangeResponse(exchange.getId(), exchange.getAmount(), exchange.getMessage(), exchange.getCard().getId()));
        }

        if (exchanges.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), exchanges.getNumber(), exchanges.getSize(),
                    exchanges.getTotalElements(), exchanges.getTotalPages(), exchanges.isLast());
        }
        return new PagedResponse<>(exchangeRespons, exchanges.getNumber(), exchanges.getSize(), exchanges.getTotalElements(),
                exchanges.getTotalPages(), exchanges.isLast());

    }

    @Override
    public ExchangeResponse getExchange(Long id) {
        Exchange exchange = exchangeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PHOTO, ID, id));

        return new ExchangeResponse(exchange.getId(), exchange.getAmount(), exchange.getMessage(), exchange.getCard().getId());
    }

    @Override
    public ExchangeResponse updateExchange(Long id, ExchangeRequest exchangeRequest, UserPrincipal currentUser) {
        Card card = cardRepository.findById(exchangeRequest.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException(ALBUM, ID, exchangeRequest.getCardId()));
        Exchange exchange = exchangeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PHOTO, ID, id));
        if (exchange.getCard().getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            exchange.setAmount(exchangeRequest.getAmount());
            exchange.setMessage(exchangeRequest.getMessage());
            exchange.setCard(card);
            Exchange updatedExchange = exchangeRepository.save(exchange);
            return new ExchangeResponse(updatedExchange.getId(), updatedExchange.getAmount(), updatedExchange.getMessage(), updatedExchange.getCard().getId());
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update this photo");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ExchangeResponse addExchange(ExchangeRequest exchangeRequest, UserPrincipal currentUser) {
        Card card = cardRepository.findById(exchangeRequest.getCardId())
                .orElseThrow(() -> new ResourceNotFoundException(ALBUM, ID, exchangeRequest.getCardId()));
        if (card.getUser().getId().equals(currentUser.getId())) {
            Exchange exchange = new Exchange(exchangeRequest.getAmount(), exchangeRequest.getMessage(), card);
            Exchange newExchange = exchangeRepository.save(exchange);
            return new ExchangeResponse(newExchange.getId(), newExchange.getAmount(), newExchange.getMessage(), newExchange.getCard().getId());
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to add photo in this album");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteExchange(Long id, UserPrincipal currentUser) {
        Exchange exchange = exchangeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PHOTO, ID, id));
        if (exchange.getCard().getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            exchangeRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "Photo deleted successfully");
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this photo");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public PagedResponse<ExchangeResponse> getAllExchangeByCard(Long albumId, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, AppConstants.CREATED_AT);

        Page<Exchange> photos = exchangeRepository.findByCardId(albumId, pageable);

        List<ExchangeResponse> exchangeRespons = new ArrayList<>(photos.getContent().size());
        for (Exchange exchange : photos.getContent()) {
            exchangeRespons.add(new ExchangeResponse(exchange.getId(), exchange.getAmount(), exchange.getMessage(), exchange.getCard().getId()));
        }

        return new PagedResponse<>(exchangeRespons, photos.getNumber(), photos.getSize(), photos.getTotalElements(),
                photos.getTotalPages(), photos.isLast());
    }
}
