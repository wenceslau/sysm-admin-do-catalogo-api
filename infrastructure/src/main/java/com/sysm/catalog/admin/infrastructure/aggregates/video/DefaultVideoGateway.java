package com.sysm.catalog.admin.infrastructure.aggregates.video;

import com.sysm.catalog.admin.domain.Identifier;
import com.sysm.catalog.admin.domain.aggregates.video.Video;
import com.sysm.catalog.admin.domain.aggregates.video.VideoGateway;
import com.sysm.catalog.admin.domain.aggregates.video.VideoID;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoPreview;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoSearchQuery;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.infrastructure.aggregates.video.persistence.VideoJpaEntity;
import com.sysm.catalog.admin.infrastructure.aggregates.video.persistence.VideoRepository;
import com.sysm.catalog.admin.infrastructure.configuration.EventService;
import com.sysm.catalog.admin.infrastructure.configuration.annotations.VideoCreatedQueue;
import com.sysm.catalog.admin.infrastructure.utils.SqlUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.sysm.catalog.admin.domain.utils.CollectionUtils.mapTo;
import static com.sysm.catalog.admin.domain.utils.CollectionUtils.nullIfEmpty;

@Component
public class DefaultVideoGateway implements VideoGateway {

    private final EventService eventService;
    private final VideoRepository videoRepository;

    public DefaultVideoGateway(
            @VideoCreatedQueue EventService eventService,
            final VideoRepository videoRepository) {
        this.eventService = eventService;
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }


    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var aVideoId = anId.getValue();
        if (this.videoRepository.existsById(aVideoId)) {
            this.videoRepository.deleteById(aVideoId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(final VideoID anId) {
        return this.videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    @Transactional
    public Video update(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public Pagination<VideoPreview> findAll(final VideoSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var actualPage = this.videoRepository.findAll(
                SqlUtils.like(SqlUtils.upper(aQuery.terms())),
                nullIfEmpty(mapTo(aQuery.castMembers(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.categories(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.genres(), Identifier::getValue)),
                page
        );

        return new Pagination<>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );
    }

    private Video save(final Video aVideo) {
        final var result = this.videoRepository.save(VideoJpaEntity.from(aVideo))
                .toAggregate();

        aVideo.publishDomainEvents(this.eventService::send);

        return result;
    }
}