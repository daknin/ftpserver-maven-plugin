/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.daknin.ftpserver.plugin;

import java.util.Properties;

import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.Md5PasswordEncryptor;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.UserManagerFactory;

/**
 * Factory for the properties file based <code>UserManager</code> implementation.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class PropertiesUserManagerFactory implements UserManagerFactory {

    private String username;

    private Properties userProperties;

    private PasswordEncryptor passwordEncryptor = new ClearTextPasswordEncryptor();

    /**
     * Creates a {@link PropertiesUserManager} instance based on the provided configuration
     */
    public UserManager createUserManager() {
            return new PropertiesUserManager(passwordEncryptor, username, userProperties);
    }

    /**
     * Get the admin name.
     * @return The admin user name
     */
    public String getAdminName() {
        return username;
    }

    /**
     *
     * @param adminName
     *            The  user name
     */
    public void setAdminName(String adminName) {
        this.username = username;
    }

    /**
     * Retrieve the password encryptor used by user managers created by this factory
     * @return The password encryptor. Default to {@link Md5PasswordEncryptor}
     *  if no other has been provided
     */
    public PasswordEncryptor getPasswordEncryptor() {
        return passwordEncryptor;
    }

    /**
     * Set the password encryptor to use by user managers created by this factory
     * @param passwordEncryptor The password encryptor
     */
    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    public Properties getUserProperties() {
        return userProperties;
    }

    public void setUserProperties(Properties userProperties) {
        this.userProperties = userProperties;
    }
}
