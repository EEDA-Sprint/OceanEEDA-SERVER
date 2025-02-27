package com.oceaneeda.server.domain.marking.domain;

import com.oceaneeda.server.domain.file.domain.File;
import com.oceaneeda.server.domain.marking.domain.value.Category;
import com.oceaneeda.server.domain.user.domain.User;
import com.oceaneeda.server.global.constants.MongoCollections;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = MongoCollections.MARKING)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Marking {

    @Id
    private ObjectId id;

    private ObjectId userId;

    private ObjectId regionId;

    private String title;

    private String content;

    private String poster;

    private List<String> trashTypes;

    private GeoJsonPoint location;

    private List<File> files;

    @CreatedDate
    private LocalDateTime createdAt;

    private Boolean isApproved;

    private Category category;

    @Builder
    public Marking(ObjectId regionId,
                   String title,
                   String content,
                   String poster,
                   List<String> trashTypes,
                   GeoJsonPoint location,
                   List<File> files,
                   Boolean isApproved,
                   Category category) {
        this.regionId = regionId;
        this.title = title;
        this.content = content;
        this.poster = poster;
        this.trashTypes = trashTypes;
        this.location = location;
        this.files = files;
        this.isApproved = isApproved;
        this.category = category;
    }

    public void update(Marking marking) {
        if (marking.regionId != null) this.regionId = marking.regionId;
        if (marking.title != null) this.title = marking.title;
        if (marking.content != null) this.content = marking.content;
        if (marking.poster != null) this.poster = marking.poster;
        if (marking.trashTypes != null) this.trashTypes = marking.trashTypes;
        if (marking.location != null) this.location = marking.location;
        if (marking.files != null) this.files = marking.files;
        if (marking.isApproved != null) this.isApproved = marking.isApproved;
        if (marking.category != null) this.category = marking.category;
    }

    public void updateUser(User user) {
        this.userId = user.getId();
    }
}
