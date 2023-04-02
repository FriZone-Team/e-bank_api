package com.onionit.ebank.service;

import com.onionit.ebank.exception.UnauthorizedException;
import com.onionit.ebank.model.Category;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

	PagedResponse<Category> getAllCategories(int page, int size);

	ResponseEntity<Category> getCategory(Long id);

	ResponseEntity<Category> addCategory(Category category, UserPrincipal currentUser);

	ResponseEntity<Category> updateCategory(Long id, Category newCategory, UserPrincipal currentUser)
			throws UnauthorizedException;

	ResponseEntity<ApiResponse> deleteCategory(Long id, UserPrincipal currentUser) throws UnauthorizedException;

}
