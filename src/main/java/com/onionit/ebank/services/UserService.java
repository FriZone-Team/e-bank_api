package com.onionit.ebank.services;

import com.onionit.ebank.models.User;
import com.onionit.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends RepositoryService<User, UserRepository> {
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }
}
