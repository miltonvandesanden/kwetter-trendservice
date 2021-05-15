package fhict.kwetter.trendservice.mappers;

import fhict.kwetter.trendservice.messaging.dto.HashtagsDto;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;

import java.util.List;

public interface TrendMapperDelegate
{
    List<Trend> mapToTrends(HashtagsDto hashtagsDto);
    List<Trend> mapToTrends(List<TrendDto> trendDtos);
    Trend mapToTrend(String hashtag);
    Trend mapToTrend(TrendDto trendDto);
}
