package fhict.kwetter.trendservice.mappers;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrendDtoMapper implements TrendDtoMapperDelegate
{
    public TrendDto mapToTrendDto(Trend trend)
    {
        return TrendDto.builder()
                .hashtag(trend.getHashtag())
                .count(trend.getCount())
                .build();
    }

    public List<TrendDto> mapToTrendDtos(List<Trend> trends) {
        List<TrendDto> trendDtos = new ArrayList<>();

        for (Trend trend : trends) {
            trendDtos.add(mapToTrendDto(trend));
        }

        return trendDtos;
    }
}
