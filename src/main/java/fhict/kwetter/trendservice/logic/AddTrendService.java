package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.api.AddClient;
import fhict.kwetter.trendservice.persistence.api.SumDto;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddTrendService implements AddTrendDelegate
{
    private final TrendRepository trendRepository;

    private final AddClient addClient;

    @Override
    public Trend addTrend(Trend trend) throws IllegalArgumentException
    {
        if (trend == null || trend.getHashtag().isBlank() || trend.getCount() < 1) {
            throw new IllegalArgumentException();
        }

        Trend result;

        if(!trendRepository.existsTrendByHashtag(trend.getHashtag())) {
            result = trendRepository.save(trend);
        } else {
            Optional<Trend> optionalTrend = trendRepository.findTrendByHashtag(trend.getHashtag());
            Trend existingTrend = optionalTrend.get();

            SumDto sumDto = addClient.add(existingTrend.getCount(), trend.getCount());

            if(sumDto == null) {
                existingTrend.setCount(existingTrend.getCount() + trend.getCount());
            } else {
                existingTrend.setCount(sumDto.getSum());
            }

            result = trendRepository.save(existingTrend);
        }

        return result;
    }
}
