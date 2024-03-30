package com.sysm.catalog.admin.application.video.delete;

import com.sysm.catalog.admin.application.UseCaseTest;
import com.sysm.catalog.admin.domain.exceptions.InternalErrorException;
import com.sysm.catalog.admin.domain.video.MediaResourceGateway;
import com.sysm.catalog.admin.domain.video.VideoGateway;
import com.sysm.catalog.admin.domain.video.VideoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Mock
    private MediaResourceGateway mediaResourceGateway;

    @BeforeEach
    public void setUp() {
        sleep();
        Mockito.reset(getMocks().toArray());
    }

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway, mediaResourceGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt() {
        // given
        final var expectedId = VideoID.unique();

        doNothing()
                .when(videoGateway).deleteById(any());

        doNothing()
                .when(mediaResourceGateway).clearResources(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
        verify(mediaResourceGateway).clearResources(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteVideo_shouldBeOk() {
        // given
        final var expectedId = VideoID.from("1231");

        doNothing()
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideoAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var expectedId = VideoID.from("1231");

        doThrow(InternalErrorException.with("Error on delete video", new RuntimeException()))
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertThrows(
                InternalErrorException.class,
                () -> this.useCase.execute(expectedId.getValue())
        );

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }
}