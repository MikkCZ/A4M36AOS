package cz.cvut.fel.aos.resource.params;

import cz.cvut.fel.aos.resource.params.pagination.Pagination;
import cz.cvut.fel.aos.resource.params.sorting.OrderBy;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public class QueryParams {

    private Optional<OrderBy> orderBy = Optional.empty();
    private Optional<Pagination> pagination = Optional.empty();

    public QueryParams setOrderBy(Optional<OrderBy> orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public QueryParams setOrderBy(OrderBy orderBy) {
        this.orderBy = Optional.of(orderBy);
        return this;
    }

    public QueryParams setPagination(Optional<Pagination> pagination) {
        this.pagination = pagination;
        return this;
    }

    public QueryParams setPagination(Pagination pagination) {
        this.pagination = Optional.of(pagination);
        return this;
    }
}
