package one.kii.kiimate.status.cases.spi;

import one.kii.summer.io.exception.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 4/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang")
@SpringBootTest(classes = {TestRefreshStatusSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestRefreshStatusSpi {


    @Autowired
    private RefreshStatusSpi refreshStatusSpi;

    @Test
    public void testGntForm() {
        RefreshStatusSpi.NameForm<Token1> form = new RefreshStatusSpi.NameForm<>();
        form.setOwnerId("wangyj");
        form.setGroup("test-token");
        form.setName("default");
        form.setTree("master");
        form.setObject(new Token1());
        try {
            refreshStatusSpi.commit(form);
        } catch (Panic | Forbidden | BadRequest | NotFound | Conflict oops) {
            oops.printStackTrace();
        }
    }

    @Test
    public void testSubIdForm() {
        RefreshStatusSpi.IdForm<Token2> form = new RefreshStatusSpi.IdForm<>();
        form.setSubId("48982345f8c901d266ced0cfb6cd471654b2105fd18bbd512d5b266358dcbace");
        form.setOwnerId("wangyj");
        form.setObject(new Token2());
        try {
            refreshStatusSpi.commit(form);
        } catch (Panic | Forbidden | BadRequest | NotFound | Conflict oops) {
            oops.printStackTrace();
        }
    }

    public static class Token1 {
        int expiresIn = 10;

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }
    }

    public static class Token2 {
        int expiresIn = 11;

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }
    }

}
