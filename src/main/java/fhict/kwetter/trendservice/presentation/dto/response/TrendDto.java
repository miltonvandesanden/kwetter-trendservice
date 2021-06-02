package fhict.kwetter.trendservice.presentation.dto.response;

import fhict.kwetter.trendservice.presentation.dto.Dto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class TrendDto implements Dto
{
    @Id
    private String hashtag;
    private int count;
}
