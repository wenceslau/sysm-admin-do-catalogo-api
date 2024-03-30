package com.sysm.catalog.admin.domain.exceptions;


import com.sysm.catalog.admin.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}