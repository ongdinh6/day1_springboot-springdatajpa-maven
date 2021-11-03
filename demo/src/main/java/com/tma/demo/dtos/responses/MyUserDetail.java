package com.tma.demo.dtos.responses;

import com.tma.demo.entities.jpa.UserJPA;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class MyUserDetail extends User implements UserDetails {
    UserJPA userJPA;

    public MyUserDetail(UserJPA userJPA, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userJPA = userJPA;
    }

    public static MyUserDetail getPrincipal() {
        try {
            return (MyUserDetail) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static UserJPA getUserIns() {
        if (getPrincipal() == null) return null;
        return getPrincipal().getUserJPA();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userJPA.getPassword();
    }

    @Override
    public String getUsername() {
        return userJPA.getUsername();
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
