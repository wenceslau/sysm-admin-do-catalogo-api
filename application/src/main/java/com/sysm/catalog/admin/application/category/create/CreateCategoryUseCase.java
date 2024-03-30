package com.sysm.catalog.admin.application.category.create;

import com.sysm.catalog.admin.application.UseCase;
import com.sysm.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
