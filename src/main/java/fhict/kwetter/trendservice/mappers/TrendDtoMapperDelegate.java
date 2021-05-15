package fhict.kwetter.trendservice.mappers;

import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;

import java.util.List;

public interface TrendDtoMapperDelegate
{
    TrendDto mapToTrendDto(Trend trend);
    List<TrendDto> mapToTrendDtos(List<Trend> trends);
}
