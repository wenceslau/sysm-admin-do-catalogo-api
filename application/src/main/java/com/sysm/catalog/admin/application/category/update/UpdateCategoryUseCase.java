package com.sysm.catalog.admin.application.category.update;

import com.sysm.catalog.admin.application.UseCase;
import com.sysm.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
