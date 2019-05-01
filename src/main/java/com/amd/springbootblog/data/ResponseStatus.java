package com.amd.springbootblog.data;

import lombok.Getter;

public enum ResponseStatus {

    OK(200, "Ok"),

    SUCCESS(201, "Created"),

    BAD_REQUEST(400, "Bad Request"),

    CREATED(201, "Created"),

    NOT_FOUND(404, "Not Found"),

    NO_DATA(204, "No Data"),

    CONFLICT(409, "Conflict"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    FIELD_ERROR(400, "Bad Request");

    @Getter
    private final int value;

    @Getter
    private final String reasonPhrase;

    ResponseStatus(Integer value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }
}
