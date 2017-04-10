package one.kii.statemate.core.spi;

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
@SpringBootTest(classes = {TestSaveMultiValueStateSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestSaveMultiValueStateSpi {

    private ThisIsAMultiValueSpringBootConfiguration conf = new ThisIsAMultiValueSpringBootConfiguration();

    @Autowired
    private SaveStateSpi saveStateSpi;

    @Test
    public void test() {
        SaveStateSpi.Form form = new SaveStateSpi.Form();
        form.setGroup("test-sub-multi-value-group");
        form.setName("default");
        form.setObject(conf);
        saveStateSpi.save(form);
    }

    class ThisIsAMultiValueSpringBootConfiguration {

        public Spring spring = new Spring();

        public Server server = new Server();

        public Logging[] logging = new Logging[]{new Logging()};

    }

    class Spring {
        DataSource datasource = new DataSource();
        Profiles profile = new Profiles();
    }

    class Profiles {
        String[] active = new String[]{"common", "dev"};
    }


    class DataSource {

        public String driverClassName = "multidriver";

        public String url = "multiurl";

        public String username = "multiusername";

        public String password = "multipassword";
    }

    class Server {
        public int[] port = new int[]{8080, 9091};
    }

    class Logging {
        public String level = "Debug";
    }

}
