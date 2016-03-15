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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.AnonymousAuthentication;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.AbstractUserManager;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.ftpserver.util.BaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <strong>Internal class, do not use directly.</strong>
 *
 * <p>Properties file based <code>UserManager</code> implementation. We use
 * <code>user.userProperties</code> file to store user data.</p>
 *
 * <p>The file will use the following userProperties for storing users:</p>
 * <table summary="">
 * <tr>
 *      <th>Property</th>
 *      <th>Documentation</th>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.homedirectory</td>
 *      <td>Path to the home directory for the user, based on the file system implementation used</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.userpassword</td>
 *      <td>The password for the user. Can be in clear text, MD5 hash or salted SHA hash based on the
 *              configuration on the user manager
 *      </td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.enableflag</td>
 *      <td>true if the user is enabled, false otherwise</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.writepermission</td>
 *      <td>true if the user is allowed to upload files and create directories, false otherwise</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.idletime</td>
 *      <td>The number of seconds the user is allowed to be idle before disconnected.
 *              0 disables the idle timeout
 *      </td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.maxloginnumber</td>
 *      <td>The maximum number of concurrent logins by the user. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.maxloginperip</td>
 *      <td>The maximum number of concurrent logins from the same IP address by the user. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.uploadrate</td>
 *      <td>The maximum number of bytes per second the user is allowed to upload files. 0 disables the check.</td>
 * </tr>
 * <tr>
 *      <td>ftpserver.user.{username}.downloadrate</td>
 *      <td>The maximum number of bytes per second the user is allowed to download files. 0 disables the check.</td>
 * </tr>
 * </table>
 *
 * <p>Example:</p>
 * <pre>
 * ftpserver.user.admin.homedirectory=/ftproot
 * ftpserver.user.admin.userpassword=admin
 * ftpserver.user.admin.enableflag=true
 * ftpserver.user.admin.writepermission=true
 * ftpserver.user.admin.idletime=0
 * ftpserver.user.admin.maxloginnumber=0
 * ftpserver.user.admin.maxloginperip=0
 * ftpserver.user.admin.uploadrate=0
 * ftpserver.user.admin.downloadrate=0
 * </pre>
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class PropertiesUserManager extends AbstractUserManager {

    private final Logger LOG = LoggerFactory
            .getLogger(PropertiesUserManager.class);

    private final static String PREFIX = "ftpserver.user.";

    private BaseProperties userDataProp;

    /**
     * Internal constructor, do not use directly. Use {@link PropertiesUserManagerFactory} instead.
     * @param passwordEncryptor passwordEncryptor
     * @param adminName adminName
     * @param userProperties userProperties
     */
    public PropertiesUserManager(PasswordEncryptor passwordEncryptor, String adminName, Properties userProperties) {
        super(adminName, passwordEncryptor);
        loadFromProperties(userProperties);
    }

    private void loadFromProperties(Properties properties) {
        userDataProp = new BaseProperties();

        if (properties != null) {
            LOG.debug("Properties supplied, will try loading");

            for (Object key: properties.keySet()) {
                String stringKey = (String) key;
                userDataProp.put(key, properties.getProperty(stringKey));
            }
        }
    }

    @Override
    public void save(User user) throws FtpException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String s) throws FtpException {
        throw new UnsupportedOperationException();
    }

    /**
     * Get all user names.
     */
    @Override
    public String[] getAllUserNames() {
        // get all user names
        String suffix = '.' + ATTR_HOME;
        ArrayList<String> ulst = new ArrayList<String>();
        Enumeration<?> allKeys = userDataProp.propertyNames();
        int prefixlen = PREFIX.length();
        int suffixlen = suffix.length();
        while (allKeys.hasMoreElements()) {
            String key = (String) allKeys.nextElement();
            if (key.endsWith(suffix)) {
                String name = key.substring(prefixlen);
                int endIndex = name.length() - suffixlen;
                name = name.substring(0, endIndex);
                ulst.add(name);
            }
        }

        Collections.sort(ulst);
        return ulst.toArray(new String[0]);
    }

    /**
     * Load user data.
     */
    @Override
    public User getUserByName(String userName) {
        if (!doesExist(userName)) {
            return null;
        }

        String baseKey = PREFIX + userName + '.';
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setEnabled(userDataProp.getBoolean(baseKey + ATTR_ENABLE, true));
        user.setHomeDirectory(userDataProp
                .getProperty(baseKey + ATTR_HOME, "/"));

        List<Authority> authorities = new ArrayList<Authority>();

        if (userDataProp.getBoolean(baseKey + ATTR_WRITE_PERM, false)) {
            authorities.add(new WritePermission());
        }

        int maxLogin = userDataProp.getInteger(baseKey + ATTR_MAX_LOGIN_NUMBER,
                0);
        int maxLoginPerIP = userDataProp.getInteger(baseKey
                + ATTR_MAX_LOGIN_PER_IP, 0);

        authorities.add(new ConcurrentLoginPermission(maxLogin, maxLoginPerIP));

        int uploadRate = userDataProp.getInteger(
                baseKey + ATTR_MAX_UPLOAD_RATE, 0);
        int downloadRate = userDataProp.getInteger(baseKey
                + ATTR_MAX_DOWNLOAD_RATE, 0);

        authorities.add(new TransferRatePermission(downloadRate, uploadRate));

        user.setAuthorities(authorities);

        user.setMaxIdleTime(userDataProp.getInteger(baseKey
                + ATTR_MAX_IDLE_TIME, 0));

        return user;
    }

    /**
     * User existance check
     */
    @Override
    public boolean doesExist(String name) {
        String key = PREFIX + name + '.' + ATTR_HOME;
        return userDataProp.containsKey(key);
    }

    /**
     * User authenticate method
     */
    @Override
    public User authenticate(Authentication authentication)
            throws AuthenticationFailedException {
        if (authentication instanceof UsernamePasswordAuthentication) {
            UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication) authentication;

            String user = upauth.getUsername();
            String password = upauth.getPassword();

            if (user == null) {
                throw new AuthenticationFailedException("Authentication failed");
            }

            if (password == null) {
                password = "";
            }

            String storedPassword = userDataProp.getProperty(PREFIX + user
                    + '.' + ATTR_PASSWORD);

            if (storedPassword == null) {
                // user does not exist
                throw new AuthenticationFailedException("Authentication failed");
            }

            if (getPasswordEncryptor().matches(password, storedPassword)) {
                return getUserByName(user);
            } else {
                throw new AuthenticationFailedException("Authentication failed");
            }

        } else if (authentication instanceof AnonymousAuthentication) {
            if (doesExist("anonymous")) {
                return getUserByName("anonymous");
            } else {
                throw new AuthenticationFailedException("Authentication failed");
            }
        } else {
            throw new IllegalArgumentException(
                    "Authentication not supported by this user manager");
        }
    }
}
