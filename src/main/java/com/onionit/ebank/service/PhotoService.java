package com.onionit.ebank.service;

import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.payload.PhotoRequest;
import com.onionit.ebank.payload.PhotoResponse;
import com.onionit.ebank.security.UserPrincipal;

public interface PhotoService {

	PagedResponse<PhotoResponse> getAllPhotos(int page, int size);

	PhotoResponse getPhoto(Long id);

	PhotoResponse updatePhoto(Long id, PhotoRequest photoRequest, UserPrincipal currentUser);

	PhotoResponse addPhoto(PhotoRequest photoRequest, UserPrincipal currentUser);

	ApiResponse deletePhoto(Long id, UserPrincipal currentUser);

	PagedResponse<PhotoResponse> getAllPhotosByAlbum(Long albumId, int page, int size);

}