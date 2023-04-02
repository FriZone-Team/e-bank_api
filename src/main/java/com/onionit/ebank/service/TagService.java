package com.onionit.ebank.service;

import com.onionit.ebank.model.Tag;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.UserPrincipal;

public interface TagService {

	PagedResponse<Tag> getAllTags(int page, int size);

	Tag getTag(Long id);

	Tag addTag(Tag tag, UserPrincipal currentUser);

	Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser);

	ApiResponse deleteTag(Long id, UserPrincipal currentUser);

}
