package fhict.kwetter.trendservice.presentation.controller;

import fhict.kwetter.trendservice.logic.GetTrendsDelegate;
import fhict.kwetter.trendservice.mappers.TrendDtoMapperDelegate;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.DtoList;
import fhict.kwetter.trendservice.presentation.dto.DtoWrapper;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrendControllerTests {
    @Mock
    private TrendDtoMapperDelegate trendDtoMapperDelegate;

    @Mock
    private GetTrendsDelegate getTrendsDelegate;

    private TrendController trendController;
    private TrendController trendControllerEmptyDelegate;
    private TrendController trendControllerEmptyMapper;

    @Before
    public void init() {
        trendController = new TrendController(Optional.of(trendDtoMapperDelegate), Optional.of(getTrendsDelegate));
        trendControllerEmptyDelegate = new TrendController(Optional.of(trendDtoMapperDelegate), Optional.empty());
        trendControllerEmptyMapper = new TrendController(Optional.empty(), Optional.of(getTrendsDelegate));
    }

    @Test
    public void getTrendsEmptyDelegateTest() {
        //ARRANGE
        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyDelegate.getTrends();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendsEmptyMapperTest() {
        //ARRANGE
        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyMapper.getTrends();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendsTest() {
        //ARRANGE
        Trend trend = Trend.builder()
                .hashtag("#Test")
                .count(1)
                .build();

        List<Trend> trends = new ArrayList<>();
        trends.add(trend);

        TrendDto trendDto = TrendDto.builder()
                .hashtag("#Test")
                .count(1)
                .build();

        List<TrendDto> trendDtos = new ArrayList<>();
        trendDtos.add(trendDto);

        DtoList dtoList = DtoList.builder()
                .data(trendDtos)
                .build();

        DtoWrapper dtoWrapper = DtoWrapper.builder()
                .data(dtoList)
                .build();

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.ok(dtoWrapper);
        ResponseEntity<DtoWrapper> actualResult;

        when(getTrendsDelegate.getTrends()).thenReturn(trends);
        when(trendDtoMapperDelegate.mapToTrendDtos(trends)).thenReturn(trendDtos);

        //ACT
        actualResult = trendController.getTrends();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}
