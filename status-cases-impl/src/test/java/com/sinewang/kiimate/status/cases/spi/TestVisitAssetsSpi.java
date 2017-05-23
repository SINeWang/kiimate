package com.sinewang.kiimate.status.cases.spi;

import one.kii.kiimate.status.cases.spi.VisitAssetsSpi;
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

/**
 * Created by WangYanJiong on 23/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate")
@SpringBootTest(classes = {TestVisitAssetsSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestVisitAssetsSpi {

    @Autowired
    private VisitAssetsSpi spi;

    @Test
    public void test() {
        VisitAssetsSpi.LatestForm form = new VisitAssetsSpi.LatestForm();
        form.setName("default");
        form.setGroup("token");
        form.setOwnerId("wangyj");
        try {
            Data data = spi.visit(Data.class, form);
            Assert.assertNull(data);
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Panic panic) {
            panic.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }


    public static class Data {
        String scope;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
