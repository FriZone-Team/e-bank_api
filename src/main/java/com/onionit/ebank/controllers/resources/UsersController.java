package com.onionit.ebank.controllers.resources;

import com.onionit.ebank.controllers.annotations.ResourcesController;
import com.onionit.ebank.models.User;
import com.onionit.ebank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResourcesController
@RequestMapping("users")
public class UsersController extends BaseController<User, UserService> {
    @Autowired
    public UsersController(UserService service) {
        super(service);
    }
}
