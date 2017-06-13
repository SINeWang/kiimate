package one.kii.kiimate.status.cases.spi;

import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.junit.Assert;
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

import java.util.Map;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate")
@SpringBootTest(classes = {TestVisitRawStatusSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestVisitRawStatusSpi {

    @Autowired
    private VisitRawStatusSpi spi;

    @Test
    public void test() {
        VisitRawStatusSpi.LatestForm form = new VisitRawStatusSpi.LatestForm();
        form.setName("default");
        form.setGroup("token");
        form.setOwnerId("wangyj");
        try {
            Data data = spi.visit(Data.class, form);
            Assert.assertNotNull(data);
            Assert.assertEquals("default", data.getName());
            Assert.assertEquals("token", data.getGroup());
            Assert.assertEquals("wangyj", data.getOwnerId());
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Panic panic) {
            panic.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }


    public static class Data {
        String pubSet;
        String ownerId;
        String visibility;
        String group;
        String name;
        String stability;
        String version;
        Map<String, Object> map;

        public String getPubSet() {
            return pubSet;
        }

        public void setPubSet(String pubSet) {
            this.pubSet = pubSet;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStability() {
            return stability;
        }

        public void setStability(String stability) {
            this.stability = stability;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        public void setMap(Map<String, Object> map) {
            this.map = map;
        }
    }
}
