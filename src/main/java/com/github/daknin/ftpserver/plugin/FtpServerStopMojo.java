package com.github.daknin.ftpserver.plugin;

import org.apache.ftpserver.FtpServer;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Properties;


@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class FtpServerStopMojo extends AbstractFtpServerMojo {

    public void execute() throws MojoFailureException {

        getLog().info("Stopping FTP server...");
        Properties properties = null;
        if (mavenProject != null) {
            properties = mavenProject.getProperties();
        } else {
            throw new MojoFailureException("Can't access maven project to stop FTP server (null)");
        }

        if (properties != null) {
            FtpServer ftpServer;
            try {
                ftpServer = (FtpServer) properties.get(FtpServerConstants.FTPSERVER_KEY);
            } catch (ClassCastException e) {
                throw new MojoFailureException("Context doesn't contain a valid ftp server instance", e);
            }
            if (ftpServer == null) {
                throw new MojoFailureException("Context doesn't contain any ftp server instance");
            }
            if (!ftpServer.isStopped()) {
                ftpServer.stop();
                getLog().info("FTP server stopped.");
            } else {
                getLog().info("FTP server was stopped already");
            }
        } else {
            throw new MojoFailureException("Maven project has null properties", new NullPointerException());
        }

    }

}
