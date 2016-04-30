package com.github.daknin.ftpserver.plugin.spring;

import org.apache.ftpserver.ConnectionConfig;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link ConnectionConfigFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see ConnectionConfigFactory
 */
public class ConnectionConfigFactoryBean extends ConnectionConfigFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createConnectionConfig();
    }

    public Class<?> getObjectType() {
        return ConnectionConfig.class;
    }

    public boolean isSingleton() {
        return false;
    }

}