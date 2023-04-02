package com.onionit.ebank.service;

import com.onionit.ebank.model.user.User;
import com.onionit.ebank.payload.ApiResponse;
import com.onionit.ebank.payload.InfoRequest;
import com.onionit.ebank.payload.UserIdentityAvailability;
import com.onionit.ebank.payload.UserProfile;
import com.onionit.ebank.payload.UserSummary;
import com.onionit.ebank.security.UserPrincipal;

public interface UserService {

	UserSummary getCurrentUser(UserPrincipal currentUser);

	UserIdentityAvailability checkUsernameAvailability(String username);

	UserIdentityAvailability checkEmailAvailability(String email);

	UserProfile getUserProfile(String username);

	User addUser(User user);

	User updateUser(User newUser, String username, UserPrincipal currentUser);

	ApiResponse deleteUser(String username, UserPrincipal currentUser);

	ApiResponse giveAdmin(String username);

	ApiResponse removeAdmin(String username);

	UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

}