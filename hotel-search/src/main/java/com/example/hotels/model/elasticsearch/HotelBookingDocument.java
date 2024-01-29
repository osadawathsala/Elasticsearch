package com.example.hotels.model.elasticsearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.join.JoinField;
import java.time.Instant;
import java.util.List;

/**
 * @author Osada
 * @Date 1/29/2024
 */

@Data
@Document(indexName = "hotels")
@Setting(shards = 1, replicas = 0)
public class HotelBookingDocument {

    @Id
    @Field(type=FieldType.Integer)
    private String id = null;

    @Field(type=FieldType.Text)
    private String name = null;

    @Field(type=FieldType.Integer)
    private Integer hotelID = null;

    @Field(type=FieldType.Text)
    private String cityNameEn = null;

    private GeoPoint location = null;

    @Field(type=FieldType.Integer)
    private Integer age = null;

    @Field(type=FieldType.Boolean)
    private Boolean freePlacesAtNow = null;

    @Field(type=FieldType.Integer)
    private Integer starts = null;

    @Field(type=FieldType.Float)
    private Double rating = null;

    @Field(type=FieldType.Nested)
    private List<Comment> comments;

    @JoinTypeRelations(
            relations =
                    {
                            @JoinTypeRelation(parent = "hotel", children = "booking")
                    }
    )
    private JoinField<String> relation;

    private Double price = null;
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate = null;
}
