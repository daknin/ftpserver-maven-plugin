package com.github.daknin.ftpserver.plugin.spring;

import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link DataConnectionConfigurationFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see DataConnectionConfigurationFactory
 */
public class DataConnectionConfigurationFactoryBean extends DataConnectionConfigurationFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createDataConnectionConfiguration();
    }

    public Class<?> getObjectType() {
        return DataConnectionConfiguration.class;
    }

    public boolean isSingleton() {
        return false;
    }

}