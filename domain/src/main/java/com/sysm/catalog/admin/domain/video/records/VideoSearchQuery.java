package com.sysm.catalog.admin.domain.video.records;

import com.sysm.catalog.admin.domain.castmember.CastMemberID;
import com.sysm.catalog.admin.domain.category.CategoryID;
import com.sysm.catalog.admin.domain.genre.GenreID;

import java.util.Set;

public record VideoSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Set<CastMemberID> castMembers,
        Set<CategoryID> categories,
        Set<GenreID> genres
) {
}