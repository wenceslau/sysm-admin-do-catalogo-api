package com.sysm.catalog.admin.domain.aggregates.video;

import com.sysm.catalog.admin.domain.aggregates.resource.Resource;
import com.sysm.catalog.admin.domain.aggregates.video.enums.VideoMediaType;

import java.util.Optional;

public interface MediaResourceGateway {

    AudioVideoMedia storeAudioVideo(VideoID anId, VideoResource aResource);

    ImageMedia storeImage(VideoID anId, VideoResource aResource);

    Optional<Resource> getResource(VideoID anId, VideoMediaType aType);

    void clearResources(VideoID anId);


}