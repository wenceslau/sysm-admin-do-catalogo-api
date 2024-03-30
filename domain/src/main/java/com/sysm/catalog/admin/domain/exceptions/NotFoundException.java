package com.sysm.catalog.admin.domain.exceptions;


import com.sysm.catalog.admin.domain.AggregateRoot;
import com.sysm.catalog.admin.domain.Identifier;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {
    protected NotFoundException(String anMessage, List<Error> errors) {
        super(anMessage, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregateRootClass,
            final Identifier anId){
        final var anError = "The %s with id %s was not found".formatted(
                anAggregateRootClass.getSimpleName(), anId.getValue());
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.getMessage(), List.of(error));
    }
}
