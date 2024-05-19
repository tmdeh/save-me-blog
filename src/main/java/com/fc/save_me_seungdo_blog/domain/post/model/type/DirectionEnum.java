package com.fc.save_me_seungdo_blog.domain.post.model.type;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@AllArgsConstructor
public enum DirectionEnum {
    asc(Direction.ASC),
    desc(Direction.DESC),
    ;

    final private Sort.Direction value;
}
