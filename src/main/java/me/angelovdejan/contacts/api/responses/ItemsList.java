package me.angelovdejan.contacts.api.responses;

import org.springframework.data.domain.Page;

import java.util.List;

public class ItemsList<T> {

    private final List<T> items;
    private final Integer page;
    private final Integer totalPages;

    private ItemsList(List<T> items, Integer page, Integer totalPages) {
        this.items = items;
        this.page = page;
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public static <A> ItemsList<A> fromPage(Page<A> page) {
        return new ItemsList<A>(
                page.getContent(),
                page.getNumber() + 1,
                page.getTotalPages() == 0 ? 1 : page.getTotalPages()
        );
    }

    public static <A> ItemsList<A> fromItems(List<A> items) {
        return new ItemsList<A>(
                items,
                1,
                1
        );
    }
}
