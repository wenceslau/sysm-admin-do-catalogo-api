package com.sysm.catalog.admin.application.castamember.retrieve.get;

import com.sysm.catalog.admin.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}