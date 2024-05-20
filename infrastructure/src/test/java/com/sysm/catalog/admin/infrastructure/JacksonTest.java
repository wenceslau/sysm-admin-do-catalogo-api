package com.sysm.catalog.admin.infrastructure;

import com.sysm.catalog.admin.infrastructure.configuration.ObjectMapperConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Inherited
@JsonTest(includeFilters =
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ObjectMapperConfig.class
    )
)
@Tag("integration")
@ActiveProfiles("test-integration")
public @interface JacksonTest {
}
