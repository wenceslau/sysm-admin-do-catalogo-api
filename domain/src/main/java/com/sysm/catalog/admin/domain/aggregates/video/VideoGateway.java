package com.sysm.catalog.admin.domain.aggregates.video;

import com.sysm.catalog.admin.domain.aggregates.video.records.VideoPreview;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoSearchQuery;
import com.sysm.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {

    Video create(Video aVideo);

    void deleteById(VideoID anId);

    Optional<Video> findById(VideoID anId);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);

}