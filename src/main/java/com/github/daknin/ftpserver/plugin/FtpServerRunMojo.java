package com.github.daknin.ftpserver.plugin;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.Properties;

@Mojo(name = "run", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class FtpServerRunMojo extends AbstractFtpServerMojo {

    private FtpServer server;

    private ListenerFactory factory;

    public void execute() throws MojoFailureException {
        getLog().info("Server root is " + serverRoot.getPath());
        boolean serverRootExists = serverRoot.exists();
        if (!serverRootExists) {
            serverRootExists = serverRoot.mkdir();
        }
        if (serverRootExists) {
            initServer();
            try {
                server.start();
            } catch (FtpException e) {
                throw new MojoFailureException("Failed to start FTP server", e);
            }
        } else {
            throw new MojoFailureException("Failed to create FTP root " + serverRoot.getPath());
        }
    }

    private void initServer() throws MojoFailureException {
        getLog().debug("About to start FTP server...");
        getLog().debug("Using XML configuration file " + configLocation + "...");
        GenericApplicationContext ctx = new GenericApplicationContext();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(resourceLoader.getResource(configLocation));
        PropertiesPropertySource ftpdPropertiesPropertySource = new PropertiesPropertySource("ftpdPropertiesPropertySource", ftpdProperties);
        ctx.getEnvironment().getPropertySources().addLast(ftpdPropertiesPropertySource);
        ctx.refresh();

        server = getServer(ctx);
        if (server != null) {
            getLog().info("FTP server started on port " + ((DefaultFtpServer) server).getListener("default").getPort());
        } else {
            throw new MojoFailureException("XML configuration does not contain a server configuration");
        }

        if (mavenProject != null) {
            Properties properties = mavenProject.getProperties();
            properties.put(FtpServerConstants.FTPSERVER_KEY, server);
        } else {
            throw new MojoFailureException("Can't add ftpserver instance as maven project is null");
        }
    }

    private FtpServer getServer(GenericApplicationContext ctx) {
        if (ctx.containsBean("server")) {
            return (FtpServer) ctx.getBean("server");
        } else {
            String[] beanNames = ctx.getBeanNamesForType(FtpServer.class);
            if (beanNames.length == 1) {
                return (FtpServer) ctx.getBean(beanNames[0]);
            } else if (beanNames.length > 1) {
                getLog().debug("Using the first server defined in the configuration, named "
                        + beanNames[0]);
                return (FtpServer) ctx.getBean(beanNames[0]);
            }
        }
        return null;
    }
}
