package com.virgo.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Data
public class PageResult<E> implements Iterable<E> {
    private long total;
    private List<E> data = new ArrayList<>();


    public static <U> PageResult<U> of(Page<U> page) {
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    public PageResult(long total, E... data) {
        this.total = total;
        this.data.addAll(Arrays.asList(data));
    }

    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public <U> PageResult<U> map(Function<? super E, ? extends U> converter) {
        return new PageResult<>(total, getConvertedContent(converter));
    }

    private <U> List<U> getConvertedContent(Function<? super E, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }
}
