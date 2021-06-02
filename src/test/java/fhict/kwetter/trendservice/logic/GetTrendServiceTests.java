package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GetTrendServiceTests
{
    @Mock
    private TrendRepository trendRepository;

    private GetTrendService getTrendService;

    @Before
    public void init() {
        trendRepository = mock(TrendRepository.class);
        getTrendService = new GetTrendService(trendRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTrendHashtagNullTest() {
        //ARRANGE
        String hashtag = null;

        //ACT
        getTrendService.getTrend(hashtag);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTrendHashtagEmptyTest() {
        //ARRANGE
        String hashtag = "";

        //ACT
        getTrendService.getTrend(hashtag);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTrendHashtagSpaceTest() {
        //ARRANGE
        String hashtag = " ";

        //ACT
        getTrendService.getTrend(hashtag);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTrendHashtagNoHashtagTest() {
        //ARRANGE
        String hashtag = "Test";

        //ACT
        getTrendService.getTrend(hashtag);
    }

    @Test
    public void getTrendTrendNotFoundTest() {
        //ARRANGE
        String hashtag = "#test";

        when(trendRepository.findTrendByHashtag(hashtag)).thenReturn(Optional.empty());

        Trend expectedResult = null;
        Trend actualResult;

        //ACT
        actualResult = getTrendService.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTrendTest() {
        //ARRANGE
        String hashtag = "#test";

        Trend trend = Trend.builder()
                .hashtag(hashtag)
                .count(1)
                .build();

        when(trendRepository.findTrendByHashtag(hashtag)).thenReturn(Optional.of(trend));

        Trend expectedResult = Trend.builder()
                .hashtag(hashtag)
                .count(1)
                .build();

        Trend actualResult;

        //ACT
        actualResult = getTrendService.getTrend(hashtag);

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}
