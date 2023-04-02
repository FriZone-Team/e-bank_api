package com.onionit.ebank.service;

import com.onionit.ebank.model.Album;
import com.onionit.ebank.payload.AlbumResponse;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.payload.request.AlbumRequest;
import com.onionit.ebank.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface AlbumService {

	PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

	ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

	ResponseEntity<Album> getAlbum(Long id);

	ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

	ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

	PagedResponse<Album> getUserAlbums(String username, int page, int size);

}
