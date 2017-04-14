package one.kii.statemate.core.spi;

import one.kii.summer.io.exception.Panic;
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
@ComponentScan("com.sinewang.statemate")
@SpringBootTest(classes = {TestSaveStateSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestSaveStateSpi {

    private ThisIsASpringBootConfiguration conf = new ThisIsASpringBootConfiguration();

    @Autowired
    private SaveStateSpi saveStateSpi;

    @Test
    public void test() {
        SaveStateSpi.Form form = new SaveStateSpi.Form();
        form.setGroup("test-sub-group");
        form.setName("default");
        form.setObject(conf);
        try {
            saveStateSpi.save(form);
        } catch (Panic panic) {
            panic.printStackTrace();
        }
    }

    class DataSource {

        public String driverClassName = "mysql";

        public String url = "url";

        public String username = "name";

        public String password = "passwd";
    }

    class Server {
        public int port = 23424;
    }

    class Logging {
        public String level = "debug";
    }

    class ThisIsASpringBootConfiguration {

        public DataSource datasource = new DataSource();

        public Server server = new Server();

        public Logging logging = new Logging();

    }
}
