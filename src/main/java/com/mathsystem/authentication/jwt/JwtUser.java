package com.mathsystem.authentication.jwt;

import com.mathsystem.domain.user.repository.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUser implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static JwtUser fromUserEntityToCustomUserDetails(User user) {
        JwtUser c = new JwtUser();
        c.login = user.getEmail();
        c.password = user.getPassword();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}