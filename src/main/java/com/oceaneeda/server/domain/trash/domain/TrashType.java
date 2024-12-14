package com.oceaneeda.server.domain.trash.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trash_types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrashType {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;
}
