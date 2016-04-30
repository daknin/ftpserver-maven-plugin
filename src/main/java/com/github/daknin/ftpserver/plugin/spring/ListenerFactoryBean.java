package com.github.daknin.ftpserver.plugin.spring;

import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link ListenerFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see ListenerFactory
 */
public class ListenerFactoryBean extends ListenerFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createListener();
    }

    public Class<?> getObjectType() {
        return Listener.class;
    }

    public boolean isSingleton() {
        return false;
    }

}