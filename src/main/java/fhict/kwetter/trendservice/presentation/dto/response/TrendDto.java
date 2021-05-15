package fhict.kwetter.trendservice.presentation.dto.response;

import fhict.kwetter.trendservice.presentation.dto.Dto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TrendDto implements Dto
{
    private String hashtag;
    private int count;
}
