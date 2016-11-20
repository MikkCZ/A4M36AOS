package cz.cvut.fel.aos.entities;

import cz.cvut.fel.aos.config.DataConfig;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.test.TestConfig;
import org.junit.Test;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(
        initializers = ConfigFileApplicationContextInitializer.class,
        classes = {TestConfig.class, DataConfig.class}
)
@Transactional @Rollback
@ActiveProfiles("dev")
public class DestinationEntityTest {

    @Autowired
    private GenericEntityDao<DestinationEntity> destinationEntityDao;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

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

    public void executeInTransaction(Runnable r) {
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
}