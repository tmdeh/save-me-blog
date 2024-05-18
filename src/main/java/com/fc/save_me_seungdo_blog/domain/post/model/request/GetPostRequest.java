package com.fc.save_me_seungdo_blog.domain.post.model.request;

import com.fc.save_me_seungdo_blog.domain.post.model.type.DirectionEnum;
import com.fc.save_me_seungdo_blog.domain.post.model.type.SortByEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class GetPostRequest {

    @Min(value = 0, message = "0보다 작을 수 없습니다.")
    private Integer page;

    @Min(value = 0, message = "0보다 작을 수 없습니다.")
    private Integer size;

    @NotNull
    private SortByEnum sortBy;

    @NotNull
    private DirectionEnum direction;

    private String keyword;
}
