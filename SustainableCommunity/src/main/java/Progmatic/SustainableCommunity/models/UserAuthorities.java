package Progmatic.SustainableCommunity.models;

import lombok.Getter;
import lombok.ToString;

@Getter
public enum UserAuthorities {

    READ_ALL("READ_ALL"),
    READ_CONTACT_INFO("READ_CONTACT_INFO"),
    READ_SELF("READ_SELF"),

    MODIFY_ALL("MODIFY_ALL"),
    MODIFY_SELF("MODIFY_SELF"),
    MODIFY_ITEM("MODIFY_ITEM"),

    DELETE_ALL("DELETE_ALL"),
    DELETE_SELF("DELETE_SELF"),
    DELETE_ITEM("DELETE_ITEM"),

    UPLOAD_ITEM("UPLOAD_ITEM"),
    RESERVE_ITEM("RESERVE_ITEM"),

    RATE_ITEM("RATE_ITEM");

    private String label;

    UserAuthorities(String label) {
        this.label = label;
    }
}
