package fhict.kwetter.trendservice.presentation.controller;

import fhict.kwetter.trendservice.logic.*;
import fhict.kwetter.trendservice.mappers.TrendDtoMapper;
import fhict.kwetter.trendservice.mappers.TrendDtoMapperDelegate;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.DtoList;
import fhict.kwetter.trendservice.presentation.dto.DtoWrapper;
import fhict.kwetter.trendservice.presentation.dto.request.CreateTrendDto;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import org.apache.coyote.Response;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrendControllerTests {
    @Mock
    private TrendDtoMapperDelegate trendDtoMapperDelegate;

    @Mock
    private GetTrendsDelegate getTrendsDelegate;

    @Mock
    private GetTrendDelegate getTrendDelegate;

    @Mock
    private AddTrendDelegate addTrendDelegate;

    private TrendController trendController;
    private TrendController trendControllerEmptyDelegate;
    private TrendController trendControllerEmptyMapper;

    @Before
    public void init() {
        trendDtoMapperDelegate = mock(TrendDtoMapper.class);
        getTrendsDelegate = mock(GetTrendsDelegate.class);
        getTrendDelegate = mock(GetTrendDelegate.class);
        addTrendDelegate = mock(AddTrendDelegate.class);

        trendController = new TrendController(Optional.of(trendDtoMapperDelegate), Optional.of(getTrendsDelegate), Optional.of(getTrendDelegate), Optional.of(addTrendDelegate));
        trendControllerEmptyDelegate = new TrendController(Optional.of(trendDtoMapperDelegate), Optional.empty(), Optional.empty(), Optional.empty());
        trendControllerEmptyMapper = new TrendController(Optional.empty(), Optional.of(getTrendsDelegate), Optional.of(getTrendDelegate), Optional.of(addTrendDelegate));
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

    @Test
    public void getTrendEmptyDelegateTest() {
        //ARRANGE
        String hashtag = "@Test";

        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyDelegate.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendEmptyMapperTest() {
        //ARRANGE
        String hashtag = "@Test";

        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyMapper.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendHashtagNullTest() {
        //ARRANGE
        String hashtag = null;

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendHashtagEmptyTest() {
        //ARRANGE
        String hashtag = "";

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendHashtagSpaceTest() {
        //ARRANGE
        String hashtag = " ";

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendTrendNullTest() {
        //ARRANGE
        String hashtag = "@Test";

        when(getTrendDelegate.getTrend(hashtag)).thenReturn(null);

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.notFound().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendTest() {
        //ARRANGE
        String hashtag = "@Test";
        int count = 2;

        Trend trend = Trend.builder()
                .hashtag(hashtag)
                .count(count)
                .build();

        TrendDto trendDto = TrendDto.builder()
                .hashtag(hashtag)
                .count(count)
                .build();

        when(getTrendDelegate.getTrend(hashtag)).thenReturn(trend);
        when(trendDtoMapperDelegate.mapToTrendDto(trend)).thenReturn(trendDto);

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.ok(DtoWrapper.builder().data(trendDto).build());
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendDelegateEmptyTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag("#Test")
                .build();

        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyDelegate.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendMapperEmptyTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag("#Test")
                .build();

        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendControllerEmptyMapper.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendHashtagNullTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag(null)
                .build();

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendHashtagEmptyTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag("")
                .build();

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendHashtagSpaceTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag(" ")
                .build();

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addTrendHashtagMissingTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag("Test")
                .build();

        ResponseEntity<DtoWrapper> expectedResult = ResponseEntity.badRequest().build();
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test()
    public void addTrendTest() {
        //ARRANGE
        CreateTrendDto createTrendDto = CreateTrendDto.builder()
                .hashtag("#Test")
                .build();

        Trend trend = Trend.builder()
                .hashtag("#Test")
                .count(1)
                .build();

        Trend result = Trend.builder()
                .hashtag("#Test")
                .count(1)
                .build();

        when(addTrendDelegate.addTrend(trend)).thenReturn(result);

        TrendDto trendDto = TrendDto.builder()
                .hashtag("#Test")
                .count(1)
                .build();

        DtoWrapper dtoWrapper = DtoWrapper.builder()
                .data(trendDto)
                .build();

        ResponseEntity<DtoWrapper> expectedResult = new ResponseEntity<>(dtoWrapper, HttpStatus.CREATED);
        ResponseEntity<DtoWrapper> actualResult;

        //ACT
        actualResult = trendController.addTrend(createTrendDto);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    //ARRANGE


    //ACT


    //ASSERT

}
