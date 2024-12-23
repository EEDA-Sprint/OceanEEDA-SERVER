package com.oceaneeda.server.domain.region.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "regions")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;
}
