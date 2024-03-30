package com.sysm.catalog.admin.domain.validation.handler;
import com.sysm.catalog.admin.domain.exceptions.DomainException;
import com.sysm.catalog.admin.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public <T> T validate(Validation<T> anValidation) {

        try{
            return anValidation.validate();
        }catch (Exception ex){
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return null;
    }
}
