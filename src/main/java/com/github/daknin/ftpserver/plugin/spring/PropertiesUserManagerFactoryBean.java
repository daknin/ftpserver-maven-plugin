package com.github.daknin.ftpserver.plugin.spring;

import com.github.daknin.ftpserver.plugin.PropertiesUserManagerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link ListenerFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see PropertiesUserManagerFactory
 */
public class PropertiesUserManagerFactoryBean extends PropertiesUserManagerFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createUserManager();
    }

    public Class<?> getObjectType() {
        return PropertiesUserManagerFactory.class;
    }

    public boolean isSingleton() {
        return false;
    }

}