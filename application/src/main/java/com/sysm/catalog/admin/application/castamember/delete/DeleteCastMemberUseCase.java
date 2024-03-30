package com.sysm.catalog.admin.application.castamember.delete;


import com.sysm.catalog.admin.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase {
}