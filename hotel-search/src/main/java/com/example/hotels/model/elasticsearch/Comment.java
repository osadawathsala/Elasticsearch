package com.example.hotels.model.elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.Instant;

/**
 * @author Osada
 * @Date 1/29/2024
 */

@Data
public class Comment {

    @Field(type= FieldType.Integer)
    private Integer hotelID = null;

    @Field(type=FieldType.Text)
    private String content = null;

    @Field(type=FieldType.Integer)
    private Integer starts = null;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdDate = null;
}
