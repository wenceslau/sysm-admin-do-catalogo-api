package com.sysm.catalog.admin.infrastructure.configuration.properties.google;

import org.springframework.beans.factory.InitializingBean;

import java.util.logging.Logger;

public class GoogleCloudProperties implements InitializingBean  {

    private static final Logger log = Logger.getLogger(GoogleCloudProperties.class.getName());

    private String credentials;
    private String projectId;

    public GoogleCloudProperties() {
    }

    public String getCredentials() {
        return credentials;
    }

    public GoogleCloudProperties setCredentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public GoogleCloudProperties setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(toString());
    }

    @Override
    public String toString() {
        return "GoogleCloudProperties{" +
                //"credentials='" + credentials + '\' ' +
                "projectId='" + projectId + '\'' +
                '}';
    }
}