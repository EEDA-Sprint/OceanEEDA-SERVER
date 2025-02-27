package com.oceaneeda.server.domain.region.domain;

import com.oceaneeda.server.global.constants.MongoCollections;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = MongoCollections.REGION)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String name;

    @Builder
    public Region(String name) {
        this.name = name;
    }

    public void update(Region region) {
        if (region.name != null) this.name = region.name;
    }
}
