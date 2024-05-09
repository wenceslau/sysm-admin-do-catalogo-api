package com.sysm.catalog.admin.infrastructure.aggregates.video.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "status")
@VideoResponseTypes
public sealed interface VideoEncoderResult
        permits VideoEncoderCompleted, VideoEncoderError {

    String getStatus();
}