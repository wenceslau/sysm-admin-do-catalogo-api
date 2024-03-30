package com.sysm.catalog.admin.domain.exceptions;

import java.util.List;

public class DomainException extends NoStacktraceException {

    protected final List<Error> errors;

    protected DomainException(final String anMessage, final List<Error> errors) {
        super(anMessage);
        this.errors = errors;
    }

    public static DomainException with(final Error anError){
        return new DomainException(anError.getMessage(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors){
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
