package cz.cvut.fel.aos.test;

import cz.cvut.fel.aos.config.DataConfig;
import cz.cvut.fel.aos.dao.FlightEntityDao;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.entities.ReservationEntity;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(
        initializers = ConfigFileApplicationContextInitializer.class,
        classes = {TestConfig.class, DataConfig.class}
)
@Transactional
@Rollback
@ActiveProfiles("dev")
public abstract class AbstractDatabaseTest {

    @Autowired
    protected GenericEntityDao<ReservationEntity> reservationEntityDao;

    @Autowired
    protected GenericEntityDao<DestinationEntity> destinationEntityDao;

    @Autowired
    protected FlightEntityDao flightEntityDao;

    @Autowired
    private PlatformTransactionManager transactionManager;
    protected TransactionTemplate transactionTemplate;

    protected void executeInTransaction(Runnable r) {
        if (transactionTemplate == null) {
            transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                r.run();
            }
        });
    }

    @Before
    public void clearDatabase() {
        executeInTransaction(() -> {
            reservationEntityDao.getAll().forEach(reservationEntityDao::delete);
            destinationEntityDao.getAll().forEach(destinationEntityDao::delete);
            flightEntityDao.getAll().forEach(flightEntityDao::delete);
        });
    }

    protected void createEntities(DestinationEntity... entities) {
        executeInTransaction(() ->
                Arrays.stream(entities).forEach(entity ->
                        destinationEntityDao.create(entity)
                )
        );
    }

    protected void createEntities(ReservationEntity... entities) {
        executeInTransaction(() ->
                Arrays.stream(entities).forEach(entity ->
                        reservationEntityDao.create(entity)
                )
        );
    }

    protected void createEntities(FlightEntity... entities) {
        executeInTransaction(() ->
                Arrays.stream(entities).forEach(entity ->
                        flightEntityDao.create(entity)
                )
        );
    }
}
