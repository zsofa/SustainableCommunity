package Progmatic.SustainableCommunity.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static Progmatic.SustainableCommunity.models.UserAuthorities.*;

public enum UserRole {

    CUSTOMER(READ_SELF,
            DELETE_SELF,
            MODIFY_SELF,
            RESERVE_ITEM,
            RATE_ITEM,
            READ_CONTACT_INFO),

    SELLER(READ_SELF,
            DELETE_SELF,
            MODIFY_SELF,
            MODIFY_ITEM,
            DELETE_ITEM,
            UPLOAD_ITEM),

    GUEST(READ_ALL),

    ADMIN(READ_SELF,
            READ_ALL,
            READ_CONTACT_INFO,
            DELETE_SELF,
            DELETE_ALL,
            MODIFY_ALL,
            MODIFY_SELF,
            MODIFY_ITEM,
            RESERVE_ITEM,
            RATE_ITEM,
            UPLOAD_ITEM,
            DELETE_ITEM);


    public final UserAuthorities[] AUTHORITIES;

    UserRole (UserAuthorities... userAuthorities) {
        AUTHORITIES = userAuthorities;
    }

    public List<SimpleGrantedAuthority> getAuths() {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();

        for (UserAuthorities auth : AUTHORITIES) {
            auths.add(new SimpleGrantedAuthority(auth.toString()));
        }
        auths.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return auths;
    }


}
