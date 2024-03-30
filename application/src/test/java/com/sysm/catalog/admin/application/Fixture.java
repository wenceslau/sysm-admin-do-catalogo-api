package com.sysm.catalog.admin.application;

import com.sysm.catalog.admin.domain.castmember.CastMember;
import com.sysm.catalog.admin.domain.castmember.CastMemberType;
import com.sysm.catalog.admin.domain.category.Category;
import com.sysm.catalog.admin.domain.genre.Genre;
import com.sysm.catalog.admin.domain.resource.Resource;
import com.sysm.catalog.admin.domain.utils.IdUtils;
import com.sysm.catalog.admin.domain.video.AudioVideoMedia;
import com.sysm.catalog.admin.domain.video.ImageMedia;
import com.sysm.catalog.admin.domain.video.Video;
import com.sysm.catalog.admin.domain.video.enums.Rating;
import com.sysm.catalog.admin.domain.video.enums.VideoMediaType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static io.vavr.API.*;

public class Fixture {

    public static String name() {
        return  RandomStringUtils.random(10, true, false);
    }

    public static Integer year() {
        return RandomUtils.nextInt(2000, 2022);
    }

    public static Double duration() {
        return RandomUtils.nextDouble(10, 120);
    }

    public static boolean bool() {
        return List.of(true, false).get(new Random().nextInt(2));
    }

    public static String title() {
        return RandomStringUtils.random(30, true, false);
    }

    public static String checksum() {
        return "03fe62de";
    }

    public static Video video() {
        return Video.newVideo(
                Fixture.title(),
                Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                Videos.rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId(), CastMembers.gabriel().getId())
        );
    }

    public static final class Categories {

        private static final Category AULAS =
                Category.newCategory("Aulas", "Some description", true);

        private static final Category LIVES =
                Category.newCategory("Lives", "Some description", true);

        public static Category aulas() {
            return AULAS.clone();
        }

        public static Category lives() {
            return LIVES.clone();
        }
    }

    public static final class CastMembers {

        private static final CastMember WESLEY =
                CastMember.newMember("Wesley FullCycle", CastMemberType.ACTOR);

        private static final CastMember GABRIEL =
                CastMember.newMember("Gabriel FullCycle", CastMemberType.ACTOR);

        public static CastMemberType type() {
            return List.of(CastMemberType.ACTOR,CastMemberType.DIRECTOR).get(new Random().nextInt(2));
        }

        public static CastMember wesley() {
            return CastMember.with(WESLEY);
        }

        public static CastMember gabriel() {
            return CastMember.with(GABRIEL);
        }
    }

    public static final class Genres {

        private static final Genre TECH =
                Genre.newGenre("Technology", true);

        private static final Genre BUSINESS =
                Genre.newGenre("Business", true);

        public static Genre tech() {
            return Genre.with(TECH);
        }

        public static Genre business() {
            return Genre.with(BUSINESS);
        }
    }

    public static final class Videos {

        private static final Video SYSTEM_DESIGN = Video.newVideo(
                "System Design no Mercado Livre na prática",
                description(),
                Year.of(2022),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId(), CastMembers.gabriel().getId())
        );

        public static Video systemDesign() {
            return Video.with(SYSTEM_DESIGN);
        }

        public static Rating rating() {
            List<Rating> ratings = List.of(Rating.values());
            return ratings.get(new Random().nextInt(ratings.size()));
        }

        public static VideoMediaType mediaType() {
            List<VideoMediaType> mediaTypes = List.of(VideoMediaType.values());
            return mediaTypes.get(new Random().nextInt(mediaTypes.size()));
        }

        public static Resource resource(final VideoMediaType type) {
            final String contentType = Match(type).of(
                    Case($(List(VideoMediaType.VIDEO, VideoMediaType.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );

            final String checksum = IdUtils.uuid();
            final byte[] content = "Conteudo".getBytes();

            return Resource.with(checksum, content, checksum, contentType);
        }

        public static String description() {
            return RandomStringUtils.random(100, true, false);
        }

        public static AudioVideoMedia audioVideo(final VideoMediaType type) {
            final var checksum = Fixture.checksum();
            return AudioVideoMedia.with(
                    checksum,
                    type.name().toLowerCase(),
                    "/videos/" + checksum
            );
        }

        public static ImageMedia imageMedia(final VideoMediaType type) {
            final var checksum = Fixture.checksum();
            return ImageMedia.with(
                    checksum,
                    type.name().toLowerCase(),
                    "/images/" + checksum
            );
        }
    }

}