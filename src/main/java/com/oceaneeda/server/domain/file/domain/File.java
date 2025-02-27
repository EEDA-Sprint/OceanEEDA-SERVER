package com.oceaneeda.server.domain.file.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
public class File {
    private String name;
    private String path;
    private int order;

    @Builder
    public File(String name, String path, int order) {
        this.name = name;
        this.path = path;
        this.order = order;
    }
}
