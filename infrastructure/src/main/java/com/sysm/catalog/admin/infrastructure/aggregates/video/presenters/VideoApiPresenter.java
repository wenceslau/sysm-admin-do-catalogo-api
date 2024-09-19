package com.sysm.catalog.admin.infrastructure.aggregates.video.presenters;

import com.sysm.catalog.admin.application.castamember.retrieve.get.GetCastMemberByIdUseCase;
import com.sysm.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.sysm.catalog.admin.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.sysm.catalog.admin.application.video.media.upload.UploadMediaOutput;
import com.sysm.catalog.admin.application.video.retrieve.get.GetVideoByIdUseCase;
import com.sysm.catalog.admin.application.video.retrieve.get.VideoOutput;
import com.sysm.catalog.admin.application.video.retrieve.list.VideoListOutput;
import com.sysm.catalog.admin.application.video.update.UpdateVideoOutput;
import com.sysm.catalog.admin.domain.aggregates.video.AudioVideoMedia;
import com.sysm.catalog.admin.domain.aggregates.video.ImageMedia;
import com.sysm.catalog.admin.domain.pagination.Pagination;
import com.sysm.catalog.admin.infrastructure.aggregates.castmember.models.CastMemberResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.category.models.CategoryResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.genre.models.GenreResponse;
import com.sysm.catalog.admin.infrastructure.aggregates.video.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface VideoApiPresenter {

    static VideoResponse present(final VideoOutput output, GetCategoryByIdUseCase getCategoryByIdUseCase, GetGenreByIdUseCase getGenreByIdUseCase, GetCastMemberByIdUseCase getCastMemberByIdUseCase) {

        var listGenres = new HashSet<GenreResponse>();
        var listCategories = new HashSet<CategoryResponse>();
        var listCastMembers = new HashSet<CastMemberResponse>();

        output.genres().forEach(genre -> {
            var genreOutput = getGenreByIdUseCase.execute(genre);
            listGenres.add(GenreResponse.from(genreOutput));
        });

        output.categories().forEach(category -> {
            var categoryOutput = getCategoryByIdUseCase.execute(category);
            listCategories.add(CategoryResponse.from(categoryOutput));
        });

        output.castMembers().forEach(castMember -> {
            var castMemberOutput = getCastMemberByIdUseCase.execute(castMember);
            listCastMembers.add(CastMemberResponse.from(castMemberOutput));
        });

        return new VideoResponse(
            output.id(),
            output.title(),
            output.description(),
            output.launchedAt(),
            output.duration(),
            output.opened(),
            output.published(),
            output.rating().getName(),
            output.createdAt(),
            output.updatedAt(),
            present(output.banner()),
            present(output.thumbnail()),
            present(output.thumbnailHalf()),
            present(output.video()),
            present(output.trailer()),
            listCategories,
            listGenres,
            listCastMembers,
            output.categories(),
            output.genres(),
            output.castMembers()
        );
    }

    static AudioVideoMediaResponse present(final AudioVideoMedia media) {
        if (media == null) {
            return null;
        }
        return new AudioVideoMediaResponse(
            media.id(),
            media.checksum(),
            media.name(),
            media.rawLocation(),
            media.encodedLocation(),
            media.status().name()
        );
    }

    static ImageMediaResponse present(final ImageMedia image) {
        if (image == null) {
            return null;
        }
        return new ImageMediaResponse(
            image.id(),
            image.checksum(),
            image.name(),
            image.location()
        );
    }

    static UpdateVideoResponse present(final UpdateVideoOutput output) {
        return new UpdateVideoResponse(output.id());
    }

    static VideoListResponse present(final VideoListOutput output) {
        return new VideoListResponse(
            output.id(),
            output.title(),
            List.of(),
            List.of(),
            output.description(),
            output.createdAt(),
            output.updatedAt()
        );
    }

    static Pagination<VideoListResponse> present(final Pagination<VideoListOutput> page) {
        return page.map(VideoApiPresenter::present);
    }

    static UploadMediaResponse present(final UploadMediaOutput output) {
        return new UploadMediaResponse(output.videoId(), output.mediaType());
    }

    static VideoListResponse present(final VideoListOutput output,
                                     final GetVideoByIdUseCase getVideoByIdUseCase,
                                     final GetCategoryByIdUseCase getCategoryByIdUseCase,
                                     final GetGenreByIdUseCase getGenreByIdUseCase) {

        var listCategories = new ArrayList<CategoryResponse>();
        var listGenres = new ArrayList<GenreResponse>();

        var videoGetOutput = getVideoByIdUseCase.execute(output.id());
        if (videoGetOutput != null) {
            videoGetOutput.categories().forEach(category -> {
                var categoryOutput = getCategoryByIdUseCase.execute(category);
                listCategories.add(CategoryResponse.from(categoryOutput));
            });

            videoGetOutput.genres().forEach(genre -> {
                var genreOutput = getGenreByIdUseCase.execute(genre);
                listGenres.add(GenreResponse.from(genreOutput));
            });
        }

        return new VideoListResponse(
            output.id(),
            output.title(),
            listGenres,
            listCategories,
            output.description(),
            output.createdAt(),
            output.updatedAt()
        );
    }

}
