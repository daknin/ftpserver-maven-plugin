package com.github.daknin.ftpserver.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.Properties;

public abstract class AbstractFtpServerMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject mavenProject;

    @Parameter(property = "configLocation", defaultValue = "classpath:ftpd-plugin.xml")
    protected String configLocation;

    @Parameter(property = "serverRoot", defaultValue = "${project.build.directory}/ftpserver/")
    protected File serverRoot;

    @Parameter(property = "port", defaultValue = "2121")
    protected int port;

    @Parameter(property = "skip", defaultValue = "false")
    protected boolean skip;

    @Parameter(property = "username", defaultValue = "admin")
    protected String username;

    @Parameter(property = "password", defaultValue = "admin")
    protected String password;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public File getServerRoot() {
        return serverRoot;
    }

    public void setServerRoot(File serverRoot) {
        this.serverRoot = serverRoot;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public MavenProject getMavenProject() {
        return mavenProject;
    }

    public void setMavenProject(MavenProject mavenProject) {
        this.mavenProject = mavenProject;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
