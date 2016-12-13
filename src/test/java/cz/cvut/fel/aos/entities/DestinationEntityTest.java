package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.pagination.Pagination;
import cz.cvut.fel.aos.resource.params.sorting.Order;
import cz.cvut.fel.aos.resource.params.sorting.OrderBy;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DestinationEntityTest extends AbstractDatabaseTest {

    @Test
    public void createAndFind() throws Exception {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").latitude(3.1f).longitude(4.2f).build();
        createEntities(entity);
        assertThat(entity.getUrl(), is("/destination/"+entity.getId()));
        executeInTransaction(() -> {
            DestinationEntity found = destinationEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getName(), is("Prague"));
            assertThat(found.getUrl(), is("/destination/"+found.getId()));
        });
    }

    @Test
    public void getAllOrder() throws Exception {
        DestinationEntity e1 = DestinationEntity.builder().name("Prague").build();
        DestinationEntity e2 = DestinationEntity.builder().name("Brno").build();
        DestinationEntity e3 = DestinationEntity.builder().name("Berlin").build();
        DestinationEntity e4 = DestinationEntity.builder().name("London").build();
        createEntities(e1, e2, e3, e4);
        executeInTransaction(() -> {
            List<DestinationEntity> sorted = destinationEntityDao.getAll(new QueryParams().setOrderBy(new OrderBy("name", Order.ASC)));
            assertThat(destinationEntityDao.getAllCount(), is(4L));
            assertThat(sorted, hasSize(4));
            assertThat(sorted.get(0).getName(), is("Berlin"));
            assertThat(sorted.get(1).getName(), is("Brno"));
            assertThat(sorted.get(2).getName(), is("London"));
            assertThat(sorted.get(3).getName(), is("Prague"));
        });
    }

    @Test
    public void getAllPagination() throws Exception {
        DestinationEntity e1 = DestinationEntity.builder().name("Prague").build();
        DestinationEntity e2 = DestinationEntity.builder().name("Brno").build();
        DestinationEntity e3 = DestinationEntity.builder().name("Berlin").build();
        DestinationEntity e4 = DestinationEntity.builder().name("London").build();
        createEntities(e1, e2, e3, e4);
        executeInTransaction(() -> {
            List<DestinationEntity> sorted = destinationEntityDao.getAll(new QueryParams().setPagination(new Pagination(2, 1)));
            assertThat(destinationEntityDao.getAllCount(), is(4L));
            assertThat(sorted, hasSize(2));
        });
    }
}