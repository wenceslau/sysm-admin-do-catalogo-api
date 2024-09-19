package com.sysm.catalog.admin.infrastructure.api.controller;

import com.sysm.catalog.admin.application.castamember.retrieve.get.GetCastMemberByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.sysm.catalog.admin.application.video.create.CreateVideoCommand;
import com.sysm.catalog.admin.application.video.create.CreateVideoUseCase;
import com.sysm.catalog.admin.application.video.delete.DeleteVideoUseCase;
import com.sysm.catalog.admin.application.video.media.get.GetMediaCommand;
import com.sysm.catalog.admin.application.video.media.get.GetMediaUseCase;
import com.sysm.catalog.admin.application.video.media.upload.UploadMediaCommand;
import com.sysm.catalog.admin.application.video.media.upload.UploadMediaUseCase;
import com.sysm.catalog.admin.application.video.retrieve.get.GetVideoByIdUseCase;
import com.sysm.catalog.admin.application.video.retrieve.list.ListVideosUseCase;
import com.sysm.catalog.admin.application.video.update.UpdateVideoCommand;
import com.sysm.catalog.admin.application.video.update.UpdateVideoUseCase;
import com.sysm.catalog.admin.domain.aggregates.castmember.CastMemberID;
import com.sysm.catalog.admin.domain.aggregates.category.CategoryID;
import com.sysm.catalog.admin.domain.aggregates.genre.GenreID;
import com.sysm.catalog.admin.domain.aggregates.resource.Resource;
import com.sysm.catalog.admin.domain.aggregates.video.VideoResource;
import com.sysm.catalog.admin.domain.aggregates.video.enums.VideoMediaType;
import com.sysm.catalog.admin.domain.aggregates.video.records.VideoSearchQuery;
import com.sysm.catalog.admin.domain.exceptions.NotificationException;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.domain.pagination.SearchQuery;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.presenters.GenreApiPresenter;
import com.sysm.catalog.admin.infrastructure.aggregates.video.models.CreateVideoRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.video.models.UpdateVideoRequest;
import com.sysm.catalog.admin.infrastructure.aggregates.video.models.VideoListResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.video.models.VideoResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.video.presenters.VideoApiPresenter;
import com.sysm.catalog.admin.infrastructure.api.VideoAPI;
import com.sysm.catalog.admin.infrastructure.utils.HashingUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static com.sysm.catalog.admin.domain.utils.CollectionUtils.mapTo;


@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;
    private final UpdateVideoUseCase updateVideoUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;
    private final ListVideosUseCase listVideosUseCase;
    private final GetMediaUseCase getMediaUseCase;
    private final UploadMediaUseCase uploadMediaUseCase;

    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    public VideoController(
        final CreateVideoUseCase createVideoUseCase,
        final GetVideoByIdUseCase getVideoByIdUseCase,
        final UpdateVideoUseCase updateVideoUseCase,
        final DeleteVideoUseCase deleteVideoUseCase,
        final ListVideosUseCase listVideosUseCase,
        final GetMediaUseCase getMediaUseCase,
        final UploadMediaUseCase uploadMediaUseCase,
        final GetGenreByIdUseCase getGenreByIdUseCase,
        final GetCategoryByIdUseCase getCategoryByIdUseCase,
        final GetCastMemberByIdUseCase getCastMemberByIdUseCase
    ) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
        this.updateVideoUseCase = Objects.requireNonNull(updateVideoUseCase);
        this.deleteVideoUseCase = Objects.requireNonNull(deleteVideoUseCase);
        this.listVideosUseCase = Objects.requireNonNull(listVideosUseCase);
        this.getMediaUseCase = Objects.requireNonNull(getMediaUseCase);
        this.uploadMediaUseCase = Objects.requireNonNull(uploadMediaUseCase);
        this.getGenreByIdUseCase = getGenreByIdUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.getCastMemberByIdUseCase = getCastMemberByIdUseCase;
    }

    @Override
    public Pagination<VideoListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction,
            final Set<String> castMembers,
            final Set<String> categories,
            final Set<String> genres
    ) {
        final var castMemberIDs = mapTo(castMembers, CastMemberID::from);
        final var categoriesIDs = mapTo(categories, CategoryID::from);
        final var genresIDs = mapTo(genres, GenreID::from);

        final var aQuery =
                new VideoSearchQuery(page, perPage, search, sort, direction, castMemberIDs, categoriesIDs, genresIDs);

        return this.listVideosUseCase.execute(aQuery)
            .map(output -> VideoApiPresenter.present(output, this.getCategoryByIdUseCase, this.getGenreByIdUseCase));
    }

    @Override
    public ResponseEntity<?> createFull(
            final String aTitle,
            final String aDescription,
            final Integer launchedAt,
            final Double aDuration,
            final Boolean wasOpened,
            final Boolean wasPublished,
            final String aRating,
            final Set<String> categories,
            final Set<String> castMembers,
            final Set<String> genres,
            final MultipartFile videoFile,
            final MultipartFile trailerFile,
            final MultipartFile bannerFile,
            final MultipartFile thumbFile,
            final MultipartFile thumbHalfFile
    ) {
        final var aCmd = CreateVideoCommand.with(
                aTitle,
                aDescription,
                launchedAt,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                categories,
                genres,
                castMembers,
                resourceOf(videoFile),
                resourceOf(trailerFile),
                resourceOf(bannerFile),
                resourceOf(thumbFile),
                resourceOf(thumbHalfFile)
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<?> createPartial(final CreateVideoRequest payload) {
        final var aCmd = CreateVideoCommand.with(
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }


    @Override
    public VideoResponse getById(final String anId) {
        return VideoApiPresenter.present(
            this.getVideoByIdUseCase.execute(anId),
            this.getCategoryByIdUseCase,
            this.getGenreByIdUseCase,
            this.getCastMemberByIdUseCase
        );
    }

    @Override
    public ResponseEntity<?> update(final String id, final UpdateVideoRequest payload) {
        final var aCmd = UpdateVideoCommand.with(
                id,
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.updateVideoUseCase.execute(aCmd);

        return ResponseEntity.ok()
                .location(URI.create("/videos/" + output.id()))
                .body(VideoApiPresenter.present(output));
    }

    @Override
    public void deleteById(final String id) {
        this.deleteVideoUseCase.execute(id);
    }

    @Override
    public ResponseEntity<byte[]> getMediaByType(final String id, final String type) {
        final var aMedia =
                this.getMediaUseCase.execute(GetMediaCommand.with(id, type));

        return ResponseEntity.ok()
                //.contentType(MediaType.valueOf(aMedia.contentType()))
                .contentLength(aMedia.content().length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s".formatted(aMedia.name()))
                .body(aMedia.content());
    }

    @Override
    public ResponseEntity<?> uploadMediaByType(final String id, final String type, final MultipartFile media) {
        final var aType = VideoMediaType.of(type)
                .orElseThrow(() -> NotificationException.with(new Error("Invalid %s for VideoMediaType".formatted(type))));

        final var aCmd =
                UploadMediaCommand.with(id, VideoResource.with(resourceOf(media), aType));

        final var output = this.uploadMediaUseCase.execute(aCmd);

        return ResponseEntity
                .created(URI.create("/videos/%s/medias/%s".formatted(id, type)))
                .body(VideoApiPresenter.present(output));
    }

    private Resource resourceOf(final MultipartFile part) {
        if (part == null) {
            return null;
        }

        try {
            return Resource.with(
                    part.getBytes(),
                    HashingUtils.checksum(part.getBytes()),
                    part.getContentType(),
                    part.getOriginalFilename()
            );
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
