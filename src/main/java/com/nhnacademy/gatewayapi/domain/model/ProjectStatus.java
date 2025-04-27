package com.nhnacademy.gatewayapi.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProjectStatus {
    ACTIVE, DORMANT, COMPLETED;

    @JsonCreator
    public static ProjectStatus fromString(String text) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        // default
        return ACTIVE;
    }
}
