package com.sysm.catalog.admin.domain.category;

import com.sysm.catalog.admin.domain.Identifier;
import com.sysm.catalog.admin.domain.utils.IdUtils;

import java.util.Objects;

public class CategoryID extends Identifier {

    private final String value;

    public CategoryID(final String value) {
        this.value = Objects.requireNonNull(value);;
    }

    public static CategoryID unique(){
        return CategoryID.from(IdUtils.uuid());
    }

    public static CategoryID from(final String anId){
        return new CategoryID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CategoryID that = (CategoryID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
