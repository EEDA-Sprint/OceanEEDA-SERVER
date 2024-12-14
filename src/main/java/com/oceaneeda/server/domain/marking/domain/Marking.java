package com.oceaneeda.server.domain.marking.domain;

import com.oceaneeda.server.domain.marking.domain.value.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;




/**
 * Marking 엔티티는 사용자 제출한 마킹 데이터를 저장합니다.
 * - userId: 사용자의 ID
 * - 지역 정보 (regionId, GeoPoint)
 * - 분류 정보 (category, trashTypes)
 * - 승인 여부 (isApproved)
 */
@Document(collection = "markings")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Marking {

    @Id
    private ObjectId id;

    private String userId;

    private String regionId;

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


    @Getter
    @Setter
    public static class File {
        private String name;
        private String path;
    }
}
