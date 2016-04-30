package com.github.daknin.ftpserver.plugin.spring;


import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring {@link FactoryBean} which extends {@link FtpServerFactory}
 * making it easier to use Spring's standard &lt;bean&gt; tag instead of
 * FtpServer's custom XML tags to configure things.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see FtpServerFactory
 */
public class FtpServerFactoryBean extends FtpServerFactory implements FactoryBean {

    public Object getObject() throws Exception {
        return createServer();
    }

    public Class<?> getObjectType() {
        return FtpServer.class;
    }

    public boolean isSingleton() {
        return false;
    }

}