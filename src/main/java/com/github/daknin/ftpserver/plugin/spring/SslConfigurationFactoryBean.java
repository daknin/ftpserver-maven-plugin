package com.github.daknin.ftpserver.plugin.spring;

import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link SslConfigurationFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see SslConfigurationFactory
 */
public class SslConfigurationFactoryBean extends SslConfigurationFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createSslConfiguration();
    }

    public Class<?> getObjectType() {
        return SslConfiguration.class;
    }

    public boolean isSingleton() {
        return false;
    }
}