package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetTrendService implements GetTrendDelegate
{
    private final TrendRepository trendRepository;

    @Override
    public Trend getTrend(String hashtag) throws IllegalArgumentException
    {
        if(hashtag == null || hashtag.isBlank() || !hashtag.contains("#")) {
            throw new IllegalArgumentException();
        }

        Optional<Trend> result = trendRepository.findTrendByHashtag(hashtag);

        if(result.isEmpty()) {
            return null;
        }

        return result.get();
    }
}
