package fhict.kwetter.trendservice.presentation.dto;

import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DtoList implements Dto
{
    private List<TrendDto> data;
}
