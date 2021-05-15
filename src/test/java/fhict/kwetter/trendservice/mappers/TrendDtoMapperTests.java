package fhict.kwetter.trendservice.mappers;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrendDtoMapperTests {
    private TrendDtoMapper trendDtoMapper;

    @Before
    public void init() {
        trendDtoMapper = new TrendDtoMapper();
    }

    @Test
    public void mapDtoTest()
    {
        //ARRANGE
        Trend trendInsert = Trend.builder()
                .hashtag("#Test")
                .count(2)
                .build();

        TrendDto expectedResult = TrendDto.builder()
                .hashtag("#Test")
                .count(2)
                .build();

        TrendDto actualResult;

        //ACT
        actualResult = trendDtoMapper.mapToTrendDto(trendInsert);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NullPointerException.class)
    public void mapDtoNullTest() {
        //ARRANGE
        Trend trendInsert = null;

        //ACT
        trendDtoMapper.mapToTrendDto(trendInsert);
    }

    @Test
    public void mapDtosTest() {
        //ARRANGE
        List<Trend> trendsInsert = new ArrayList<>();
        trendsInsert.add(
                Trend.builder()
                        .hashtag("#Test")
                        .count(1)
                        .build()
        );

        List<TrendDto> expectedResult = new ArrayList<>();
        expectedResult.add(
                TrendDto.builder()
                        .hashtag("#Test")
                        .count(1)
                        .build()
        );

        List<TrendDto> actualResult;

        //ACT
        actualResult = trendDtoMapper.mapToTrendDtos(trendsInsert);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void mapDtosEmptyTest() {
        //ARRANGE
        List<Trend> trendsInsert = new ArrayList<>();
        List<TrendDto> expectedResult = new ArrayList<>();
        List<TrendDto> actualResult;

        //ACT
        actualResult = trendDtoMapper.mapToTrendDtos(trendsInsert);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NullPointerException.class)
    public void mapDtosNullTest() {
        //ARRANGE
        List<Trend> trendsInsert = null;

        //ACT
        trendDtoMapper.mapToTrendDtos(trendsInsert);
    }
}
