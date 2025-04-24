package com.nhnacademy.gatewayapi.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CUD {
    ACTIVE, WITHDRAWN, DORMANT;

    @JsonCreator
    public static CUD from(String str){
        for (CUD cud : CUD.values()) {
            if (cud.name().equals(str)) {
                return cud;
            }
        }
        return ACTIVE;
    }
}