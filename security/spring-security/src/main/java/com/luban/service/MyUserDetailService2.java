package com.luban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Hyy
 * @title: MyUserDetailService2
 * @description: TODO
 * @date 2020/12/22
 */

@Service
public class MyUserDetailService2 implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userDetail = new User("user2", passwordEncoder.encode("111"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
        return userDetail;
    }
}
