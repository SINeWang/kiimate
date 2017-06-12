package one.kii.kiimate.model.core.dai;

import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
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
@SpringBootTest(classes = {TestLoadExtensionDai.class})
@EnableAutoConfiguration
public class TestLoadExtensionDai {

    @Autowired
    private ExtensionDai dai;


    private ExtensionDai.Record normalRecord;


    private Long ID = 100000L;


    @Before
    public void before() {
        normalRecord = new ExtensionDai.Record();
        normalRecord.setBeginTime(new Date());
        normalRecord.setEndTime(null);
        normalRecord.setGroup("testGroup");
        normalRecord.setName("testName");
        normalRecord.setId(ID);
        normalRecord.setOperatorId("testOperator");
        normalRecord.setVisibility("testVisibility");
        normalRecord.setOwnerId("testOwner");
        normalRecord.setTree("testTree");
        normalRecord.setCommit("0x0");
    }


    @Test
    public void testLoadById() throws BadRequest, Panic, Conflict, NotFound {
        dai.remember(normalRecord);
        ExtensionDai.ChannelId id = new ExtensionDai.ChannelId();
        id.setId(ID);
        ExtensionDai.Record record = null;
        record = dai.loadLast(id);
        Assert.assertNotNull(record);

        dai.forget(ID);
        try {
            dai.loadLast(id);
        } catch (NotFound notFound) {
            Assert.assertEquals(String.valueOf(ID), notFound.getReasons().getFirst("id"));
        }
    }

    @Test
    public void testLoadByName() throws BadRequest, Panic, Conflict, NotFound {
        dai.remember(normalRecord);
        ExtensionDai.ChannelName name = new ExtensionDai.ChannelName();
        name.setOwnerId("testOwnerId");
        name.setGroup("testGroup");
        name.setName("testName");
        name.setTree("testTree");

        ExtensionDai.Record record = null;
        record = dai.loadLast(name);
        Assert.assertNotNull(record);

        dai.forget(ID);
        try {
            dai.loadLast(name);
        } catch (NotFound notFound) {
            Assert.assertEquals("testName", notFound.getReasons().getFirst("name"));
            Assert.assertEquals("testGroup", notFound.getReasons().getFirst("group"));
            Assert.assertEquals("testOwnerId", notFound.getReasons().getFirst("ownerId"));
        }
    }

    @After
    public void after() {
        dai.forget(normalRecord.getId());
    }
}
