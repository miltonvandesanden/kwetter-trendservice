package fhict.kwetter.trendservice.presentation.controller;

import fhict.kwetter.trendservice.logic.GetTrendsDelegate;
import fhict.kwetter.trendservice.mappers.TrendDtoMapper;
import fhict.kwetter.trendservice.mappers.TrendDtoMapperDelegate;
import fhict.kwetter.trendservice.presentation.dto.DtoList;
import fhict.kwetter.trendservice.presentation.dto.DtoWrapper;

import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/trend")
@RequiredArgsConstructor
public class TrendController extends AbstractController {
    private final Optional<TrendDtoMapperDelegate> trendDtoMapperDelegate;

    private final Optional<GetTrendsDelegate> getTrendsDelegate;

    @GetMapping
    public ResponseEntity<DtoWrapper> getTrends() {
        if(getTrendsDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

        if(trendDtoMapperDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

        List<TrendDto> trendDtos = trendDtoMapperDelegate.get().mapToTrendDtos(call("TrendService.getHashtags", () -> getTrendsDelegate.get().getTrends()));

        DtoList dtoList = DtoList.builder()
                .data(trendDtos)
                .build();

        DtoWrapper dtoWrapper = DtoWrapper.builder()
                .data(dtoList)
                .build();

        return ResponseEntity.ok(dtoWrapper);
    }
}
