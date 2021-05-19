package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AddTrendServiceTests {
    @Mock
    private TrendRepository trendRepository;

    private AddTrendService addTrendService;

    @Before
    public void init() {
        trendRepository = mock(TrendRepository.class);
        addTrendService = new AddTrendService(trendRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTrendTestTrendNull() {
        //ARRANGE
        Trend trendInsert = null;

        //ACT
        addTrendService.addTrend(trendInsert);
    }

    @Test(expected = NullPointerException.class)
    public void addTrendTestTrendHashtagNull() {
        //ARRANGE
        Trend trendInsert = Trend.builder()
                .hashtag(null)
                .count(1)
                .build();

        //ACT
        addTrendService.addTrend(trendInsert);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTrendTestTrendHashtagBlank() {
        //ARRANGE
        Trend trend = Trend.builder()
                .hashtag("")
                .count(1)
                .build();

        //ACT
        addTrendService.addTrend(trend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTrendTestTrendCountZero() {
        //ARRANGE
        Trend trend = Trend.builder()
                .hashtag("#Test")
                .count(0)
                .build();

        //ACT
        addTrendService.addTrend(trend);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTrendTestTrendCountNegative() {
        //ARRANGE
        Trend trend = Trend.builder()
                .hashtag("#Test")
                .count(-1)
                .build();

        //ACT
        addTrendService.addTrend(trend);
    }
}
