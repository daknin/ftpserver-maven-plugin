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

    @Parameter(property = "ftpdProperties")
    protected Properties ftpdProperties;

    @Parameter(property = "serverRoot", defaultValue = "${project.build.directory}/ftpserver/")
    protected File serverRoot;

    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public Properties getFtpdProperties() {
        return ftpdProperties;
    }

    public void setFtpdProperties(Properties ftpdProperties) {
        this.ftpdProperties = ftpdProperties;
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
}
