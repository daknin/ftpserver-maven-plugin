package com.github.daknin.ftpserver.plugin;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FtpServerPluginIT {
    @Test
    public void foo() throws Exception {
        Integer port = Integer.valueOf(System.getProperty("ftp.port"));
        FTPClient ftp = new FTPClient();
        ftp.connect("localhost", port);
        ftp.login("admin", "admin");
        int reply = ftp.getReplyCode();
        InputStream stream = new ByteArrayInputStream("TEMP".getBytes(StandardCharsets.UTF_8));
        ftp.storeFile("temp.txt", stream);
        ftp.disconnect();
    }
}