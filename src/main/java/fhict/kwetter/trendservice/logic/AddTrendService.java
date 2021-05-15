package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddTrendService implements AddTrendDelegate
{
    private final TrendRepository trendRepository;

    @Override
    public Trend addTrend(Trend trend) throws IllegalArgumentException
    {
        if (trend == null || trend.getHashtag().isBlank() || trend.getCount() != 1) {
            throw new IllegalArgumentException();
        }

        Trend result;

        if(!trendRepository.existsTrendByHashtag(trend.getHashtag())) {
            result = trendRepository.save(trend);
        } else {
            Optional<Trend> optionalTrend = trendRepository.findTrendByHashtag(trend.getHashtag());
            Trend existingTrend = optionalTrend.get();
            existingTrend.setCount(existingTrend.getCount() + trend.getCount());

            result = trendRepository.save(existingTrend);
        }

        return result;
    }
}
