package Progmatic.SustainableCommunity.models;

public enum UserRole {

    CUSTOMER(UserAuthorities.READ_SELF,
            UserAuthorities.DELETE_SELF,
            UserAuthorities.MODIFY_SELF,
            UserAuthorities.RESERVE_ITEM,
            UserAuthorities.RATE_ITEM,
            UserAuthorities.READ_CONTACT_INFO),

    SELLER(UserAuthorities.READ_SELF,
            UserAuthorities.DELETE_SELF,
            UserAuthorities.MODIFY_SELF,
            UserAuthorities.MODIFY_ITEM,
            UserAuthorities.DELETE_ITEM,
            UserAuthorities.UPLOAD_ITEM),

    GUEST(UserAuthorities.READ_ALL),

    ADMIN(UserAuthorities.READ_SELF,
            UserAuthorities.READ_ALL,
            UserAuthorities.READ_CONTACT_INFO,
            UserAuthorities.DELETE_SELF,
            UserAuthorities.DELETE_ALL,
            UserAuthorities.MODIFY_ALL,
            UserAuthorities.MODIFY_SELF,
            UserAuthorities.MODIFY_ITEM,
            UserAuthorities.RESERVE_ITEM,
            UserAuthorities.RATE_ITEM,
            UserAuthorities.UPLOAD_ITEM,
            UserAuthorities.DELETE_ITEM);


    public final UserAuthorities[] AUTHORITIES;

    UserRole (UserAuthorities... userAuthorities) {
        AUTHORITIES = userAuthorities;
    }


}
