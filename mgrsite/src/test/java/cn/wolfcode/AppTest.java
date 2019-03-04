package cn.wolfcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple ApplicationMgrConfig.
 */
@Component
@Slf4j
public class AppTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testDirectQueue() {
        assertTrue( true );
    }
}
