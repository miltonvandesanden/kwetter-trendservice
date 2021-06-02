package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTrendsServiceTests
{
    @Mock
    private TrendRepository trendRepository;

    private GetTrendsService getTrendsService;

    @Before
    public void init() {
        trendRepository = mock(TrendRepository.class);
        getTrendsService = new GetTrendsService(trendRepository);
    }

    @Test
    public void getTrendsEmptyTest() {
        //ARRANGE
        List<Trend> expectedResult = new ArrayList<>();
        List<Trend> actualResult;

        when(trendRepository.findAll()).thenReturn(new ArrayList<>());

        //ACT
        actualResult = getTrendsService.getTrends();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendsTest() {
        //ARRANGE
        List<Trend> expectedResult = new ArrayList<>();
        expectedResult.add(
                Trend.builder()
                        .hashtag("#Test")
                        .count(1)
                        .build()
        );

        List<Trend> actualResult;

        List<Trend> trends = new ArrayList<>();
        trends.add(
                Trend.builder()
                    .hashtag("#Test")
                    .count(1)
                    .build()

        );

        when(trendRepository.findAll()).thenReturn(trends);

        //ACT
        actualResult = getTrendsService.getTrends();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}
