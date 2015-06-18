package com.novacroft.nemo.tfl.batch.support;

import org.junit.Ignore;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * AutowiringSpringBeanJobFactory unit tests
 */
public class AutowiringSpringBeanJobFactoryTest {
    @Test
    public void shouldSetApplicationContext() {
        AutowireCapableBeanFactory mockAutowireCapableBeanFactory = mock(AutowireCapableBeanFactory.class);

        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getAutowireCapableBeanFactory()).thenReturn(mockAutowireCapableBeanFactory);

        AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();

        autowiringSpringBeanJobFactory.setApplicationContext(mockApplicationContext);

        assertEquals(mockAutowireCapableBeanFactory, autowiringSpringBeanJobFactory.beanFactory);
    }

    // FIXME
    // No support in Mockito for mocking overridden super class methods - more work required to find an alternative approach
    @Ignore
    public void shouldCreateJobInstance() throws Exception {
        JobDataMap mockJobDataMap = mock(JobDataMap.class);

        TriggerFiredBundle mockTriggerFiredBundle = mock(TriggerFiredBundle.class);

        AutowireCapableBeanFactory mockAutowireCapableBeanFactory = mock(AutowireCapableBeanFactory.class);
        doNothing().when(mockAutowireCapableBeanFactory).autowireBean(anyObject());

        AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory = mock(AutowiringSpringBeanJobFactory.class);
        when(autowiringSpringBeanJobFactory.createJobInstance(any(TriggerFiredBundle.class))).thenCallRealMethod();
        // mock overridden super method
        when(autowiringSpringBeanJobFactory.createJobInstance(any(TriggerFiredBundle.class))).thenReturn(mockJobDataMap);

        autowiringSpringBeanJobFactory.beanFactory = mockAutowireCapableBeanFactory;

        autowiringSpringBeanJobFactory.createJobInstance(mockTriggerFiredBundle);

        verify(mockAutowireCapableBeanFactory).autowireBean(anyObject());
    }
}
