package csb.aspect

import csb.config.AppConfig
import csb.service.SubmitService
import csb.aspect.TransformLogger
import org.springframework.context.annotation.ComponentScan

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
@ComponentScan( basePackages=[ "csb.dsmodelinput","csb.config","csb.aspect" ] )
public class LogAspectTest {

    @Autowired
    private SubmitService ss

    @Autowired
    private TransformLogger tLogger

    @Test
    public void testTransformLogger() {
        assert true
    }
}