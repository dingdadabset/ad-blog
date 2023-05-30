package org.example.service;

import org.example.conf.ResponseResult;
import org.example.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
