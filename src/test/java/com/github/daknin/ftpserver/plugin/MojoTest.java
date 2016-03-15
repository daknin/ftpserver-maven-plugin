package com.github.daknin.ftpserver.plugin;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created on 30/01/2016.
 */
public class MojoTest {
    @Test
    public void testName() throws Exception {
        String userDir = System.getProperty("user.dir");
        FtpServerRunMojo startMojo = new FtpServerRunMojo();
        Model model = new Model();
        MavenProject project = new MavenProject(model);
        startMojo.setMavenProject(project);
        startMojo.setServerRoot(new File(userDir + "/target/ftpserver"));
        startMojo.setConfigLocation("classpath:ftpd-plugin.xml");
        startMojo.setUsername("myuser");
        startMojo.setPassword("mypassword");
        startMojo.setPort(2121);
        startMojo.execute();

        FTPClient ftp = new FTPClient();
        ftp.connect("localhost", 2121);
        ftp.login("myuser", "mypassword");
        int reply = ftp.getReplyCode();
        assertTrue(FTPReply.isPositiveCompletion(reply));
        assertTrue("Can't login with user", ftp.isConnected());
        ftp.disconnect();

        FtpServerStopMojo stopMojo = new FtpServerStopMojo();
        stopMojo.setMavenProject(project);

        stopMojo.execute();

        //assertNull(mojo.getServer);
    }
}
