package fhict.kwetter.trendservice.presentation.controller;

import fhict.kwetter.trendservice.logic.AddTrendDelegate;
import fhict.kwetter.trendservice.logic.GetTrendDelegate;
import fhict.kwetter.trendservice.logic.GetTrendsDelegate;
import fhict.kwetter.trendservice.mappers.TrendDtoMapper;
import fhict.kwetter.trendservice.mappers.TrendDtoMapperDelegate;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.DtoList;
import fhict.kwetter.trendservice.presentation.dto.DtoWrapper;

import fhict.kwetter.trendservice.presentation.dto.request.CreateTrendDto;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/trend")
public class TrendController extends AbstractController {
    private final Optional<TrendDtoMapperDelegate> trendDtoMapperDelegate;

    private final Optional<GetTrendsDelegate> getTrendsDelegate;
    private final Optional<GetTrendDelegate> getTrendDelegate;
    private final Optional<AddTrendDelegate> addTrendDelegate;

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

    @GetMapping("/{hashtag}")
    public ResponseEntity<DtoWrapper> getTrend(@PathVariable String hashtag) {
        if(getTrendDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

        if(trendDtoMapperDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

        if(hashtag == null || hashtag.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Trend trend = call("TrendService.getTrend", () -> getTrendDelegate.get().getTrend(hashtag));

        if(trend == null) {
            return ResponseEntity.notFound().build();
        }

        TrendDto trendDto = trendDtoMapperDelegate.get().mapToTrendDto(trend);

        DtoWrapper dtoWrapper = DtoWrapper.builder()
                .data(trendDto)
                .build();

        return ResponseEntity.ok(dtoWrapper);
    }

    @PostMapping()
    public ResponseEntity<DtoWrapper> addTrend(@RequestBody CreateTrendDto createTrendDto) {
        if(addTrendDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

        if(trendDtoMapperDelegate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }

        if(createTrendDto.getHashtag() == null || createTrendDto.getHashtag().isBlank() || !createTrendDto.getHashtag().contains("#")) {
            return ResponseEntity.badRequest().build();
        }

        Trend trend = Trend.builder()
                .hashtag(createTrendDto.getHashtag())
                .count(1)
                .build();

        Trend result = call("AddTrendService.addTrend", () -> addTrendDelegate.get().addTrend(trend));

        TrendDto trendDto = TrendDto.builder()
                .hashtag(result.getHashtag())
                .count(result.getCount())
                .build();

        DtoWrapper dtoWrapper = DtoWrapper.builder()
                .data(trendDto)
                .build();

        return new ResponseEntity<>(dtoWrapper, HttpStatus.CREATED);
    }
}
