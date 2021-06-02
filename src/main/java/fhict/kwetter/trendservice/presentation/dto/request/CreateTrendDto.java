package fhict.kwetter.trendservice.presentation.dto.request;

import fhict.kwetter.trendservice.presentation.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrendDto implements Dto
{
    private String hashtag;
}
