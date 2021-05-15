package fhict.kwetter.trendservice.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoWrapper<T extends Dto>
{
    private T data;
}
