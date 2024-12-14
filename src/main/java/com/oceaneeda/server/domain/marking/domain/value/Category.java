package com.oceaneeda.server.domain.marking.domain.value;

public enum Category {
    TRASH,
    ART;

    public static Category fromString(String value) {
        try {
            return Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + value);
        }
    }
}
