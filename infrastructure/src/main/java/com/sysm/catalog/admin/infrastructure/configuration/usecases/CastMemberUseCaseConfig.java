package com.sysm.catalog.admin.infrastructure.configuration.usecases;

import com.sysm.catalog.admin.application.castamember.create.CreateCastMemberUseCase;
import com.sysm.catalog.admin.application.castamember.create.DefaultCreateCastMemberUseCase;
import com.sysm.catalog.admin.application.castamember.delete.DefaultDeleteCastMemberUseCase;
import com.sysm.catalog.admin.application.castamember.delete.DeleteCastMemberUseCase;
import com.sysm.catalog.admin.application.castamember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.sysm.catalog.admin.application.castamember.retrieve.get.GetCastMemberByIdUseCase;
import com.sysm.catalog.admin.application.castamember.retrieve.list.DefaultListCastMembersUseCase;
import com.sysm.catalog.admin.application.castamember.retrieve.list.ListCastMembersUseCase;
import com.sysm.catalog.admin.application.castamember.update.DefaultUpdateCastMemberUseCase;
import com.sysm.catalog.admin.application.castamember.update.UpdateCastMemberUseCase;
import com.sysm.catalog.admin.domain.aggregates.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }
}