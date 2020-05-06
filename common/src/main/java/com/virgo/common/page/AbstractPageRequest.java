package com.virgo.common.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Data
@EqualsAndHashCode
public abstract class AbstractPageRequest {
    private Integer page = 0;
    private Integer size = 20;

    public Pageable pageable() {
        return PageRequest.of(page, size);
    }
}
