package one.kii.kiimate.model.core.dai;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by WangYanJiong on 12/06/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.kiimate")
@MapperScan("com.sinewang.kiimate")
@SpringBootTest(classes = {TestRememberExtensionDai.class})
@EnableAutoConfiguration
public class TestRememberExtensionDai {

    private static final Eid64Generator idgen = new Eid64Generator(1);
    @Autowired
    private ExtensionDai dai;
    private ExtensionDai.Record badRequestRecord;
    private ExtensionDai.Record normalRecord;

    @Before
    public void before() {
        badRequestRecord = new ExtensionDai.Record();
        badRequestRecord.setBeginTime(new Date());
        badRequestRecord.setEndTime(null);
        badRequestRecord.setGroup("testGroup");
        badRequestRecord.setName("testName");
        badRequestRecord.setId(idgen.born());
        badRequestRecord.setOperatorId("testOperator");
        badRequestRecord.setVisibility("testVisibility");
        badRequestRecord.setOwnerId("testOwner");
        badRequestRecord.setTree("testTree");

        normalRecord = ValueMapping.from(ExtensionDai.Record.class, badRequestRecord);
        normalRecord.setCommit("0x0");
    }


    @Test
    public void testRemember() throws BadRequest {
        try {
            dai.remember(badRequestRecord);
        } catch (Conflict conflict) {

        } catch (BadRequest badRequest) {
            Assert.assertEquals(1, badRequest.getKeys().length);
        }

        try {
            dai.remember(normalRecord);
        } catch (Conflict conflict) {
        }

        try {
            dai.remember(normalRecord);
        } catch (Conflict conflict) {
            Assert.assertEquals(5, conflict.getKeys().length);
        }
    }

    @After
    public void after() {
        dai.forget(normalRecord.getId());
    }
}
