package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.test.AbstractDatabaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}