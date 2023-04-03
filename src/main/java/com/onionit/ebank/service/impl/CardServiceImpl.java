package com.onionit.ebank.service.impl;

import com.onionit.ebank.exception.BlogapiException;
import com.onionit.ebank.exception.ResourceNotFoundException;
import com.onionit.ebank.model.Card;
import com.onionit.ebank.model.Exchange;
import com.onionit.ebank.model.role.RoleName;
import com.onionit.ebank.model.user.User;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.CardResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.payload.request.CardRequest;
import com.onionit.ebank.repository.CardRepository;
import com.onionit.ebank.repository.UserRepository;
import com.onionit.ebank.security.UserPrincipal;
import com.onionit.ebank.service.CardService;
import com.onionit.ebank.utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.onionit.ebank.utils.AppConstants.ID;

@Service
public class CardServiceImpl implements CardService {
    private static final String CREATED_AT = "createdAt";

    private static final String ALBUM_STR = "Album";

    private static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PagedResponse<CardResponse> getAllCard(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Card> cards = cardRepository.findAll(pageable);

        if (cards.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), cards.getNumber(), cards.getSize(), cards.getTotalElements(),
                    cards.getTotalPages(), cards.isLast());
        }

        List<CardResponse> cardResponse = Arrays.asList(modelMapper.map(cards.getContent(), CardResponse[].class));

        return new PagedResponse<>(cardResponse, cards.getNumber(), cards.getSize(), cards.getTotalElements(), cards.getTotalPages(),
                cards.isLast());
    }

    @Override
    public ResponseEntity<Card> addCard(CardRequest cardRequest, UserPrincipal currentUser) {
        User user = userRepository.getUser(currentUser);

        Card card = new Card();

        modelMapper.map(cardRequest, card);

        List<Exchange> exchanges = new ArrayList<>();
        card.setExchanges(exchanges);

        card.setUser(user);
        Card newCard = cardRepository.save(card);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Card> getCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CardResponse> updateCard(Long id, CardRequest newAlbum, UserPrincipal currentUser) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        User user = userRepository.getUser(currentUser);
        if (card.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            card.setCode(newAlbum.getCode());
            Card updatedCard = cardRepository.save(card);

            CardResponse cardResponse = new CardResponse();

            modelMapper.map(updatedCard, cardResponse);

            return new ResponseEntity<>(cardResponse, HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCard(Long id, UserPrincipal currentUser) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        User user = userRepository.getUser(currentUser);
        if (card.getUser().getId().equals(user.getId()) || currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            cardRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted album"), HttpStatus.OK);
        }

        throw new BlogapiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
    }

    @Override
    public PagedResponse<Card> getUserCards(String username, int page, int size) {
        User user = userRepository.getUserByName(username);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Card> albums = cardRepository.findByUserId(user.getId(), pageable);

        List<Card> content = albums.getNumberOfElements() > 0 ? albums.getContent() : Collections.emptyList();

        return new PagedResponse<>(content, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
    }
}
