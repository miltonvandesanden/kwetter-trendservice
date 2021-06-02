package fhict.kwetter.trendservice.persistence.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrendTests {
    private Trend trend;

    @Before
    public void init() {
        trend = new Trend();
    }

    @Test
    public void toStringTest() {
        //ARRANGE
        trend.setHashtag("#Test");
        trend.setCount(1);

        String expectedResult = "Trend[hashtag=#Test, count=1]";
        String actualResult;

        //ACT
        actualResult = trend.toString();

        //ASSERT
        assertEquals(expectedResult, actualResult);
    }
}
