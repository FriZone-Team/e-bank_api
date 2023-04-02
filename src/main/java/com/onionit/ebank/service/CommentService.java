package com.onionit.ebank.service;

import com.onionit.ebank.model.Comment;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.CommentRequest;
import com.onionit.ebank.payload.PagedResponse;
import com.onionit.ebank.security.UserPrincipal;

public interface CommentService {

	PagedResponse<Comment> getAllComments(Long postId, int page, int size);

	Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

	Comment getComment(Long postId, Long id);

	Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

	ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}
