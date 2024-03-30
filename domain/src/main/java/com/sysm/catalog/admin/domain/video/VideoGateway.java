package com.sysm.catalog.admin.domain.video;

import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.video.records.VideoPreview;
import com.sysm.catalog.admin.domain.video.records.VideoSearchQuery;

import java.util.Optional;

public interface VideoGateway {

    Video create(Video aVideo);

    void deleteById(VideoID anId);

    Optional<Video> findById(VideoID anId);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);

}