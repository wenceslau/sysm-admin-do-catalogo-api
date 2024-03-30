package com.sysm.catalog.admin.application.castamember.create;

import com.sysm.catalog.admin.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {

}
