package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.resource.Order;
import cz.cvut.fel.aos.resource.OrderBy;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DestinationEntityTest extends AbstractDatabaseTest {

    @Autowired
    private GenericEntityDao<DestinationEntity> destinationEntityDao;

    @Test
    public void createAndFind() throws Exception {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").latitude(3.1f).longitude(4.2f).build();
        executeInTransaction(() -> destinationEntityDao.create(entity));
        executeInTransaction(() -> {
            DestinationEntity found = destinationEntityDao.findById(entity.getId());
            assertThat(found, is(entity));
            assertThat(found.getName(), is("Prague"));
        });
    }

    @Test
    public void getAllOrder() throws Exception {
        DestinationEntity e1 = DestinationEntity.builder().name("Prague").build();
        DestinationEntity e2 = DestinationEntity.builder().name("Brno").build();
        DestinationEntity e3 = DestinationEntity.builder().name("Berlin").build();
        DestinationEntity e4 = DestinationEntity.builder().name("London").build();
        executeInTransaction(() -> {
            destinationEntityDao.create(e1);
            destinationEntityDao.create(e2);
            destinationEntityDao.create(e3);
            destinationEntityDao.create(e4);
        });
        executeInTransaction(() -> {
            List<DestinationEntity> sorted = destinationEntityDao.getAll(Optional.of(new OrderBy("name", Order.ASC)));
            assertThat(sorted, hasSize(4));
            assertThat(sorted.get(0).getName(), is("Berlin"));
            assertThat(sorted.get(1).getName(), is("Brno"));
            assertThat(sorted.get(2).getName(), is("London"));
            assertThat(sorted.get(3).getName(), is("Prague"));
        });
    }
}