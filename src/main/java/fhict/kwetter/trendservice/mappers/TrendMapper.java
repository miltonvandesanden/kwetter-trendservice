package fhict.kwetter.trendservice.mappers;

import fhict.kwetter.trendservice.messaging.dto.HashtagsDto;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.dto.response.TrendDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrendMapper implements TrendMapperDelegate
{
    @Override
    public List<Trend> mapToTrends(HashtagsDto hashtagsDto)
    {
        List<Trend> trends = new ArrayList<>();

        for(String hashtag : hashtagsDto.getHashtags()) {
            trends.add(
                    Trend.builder()
                            .hashtag(hashtag)
                            .count(1)
                            .build()
            );
        }

        return trends;
    }

    @Override
    public List<Trend> mapToTrends(List<TrendDto> trendDtos) {
        List<Trend> trends = new ArrayList<>();

        for(TrendDto trendDto : trendDtos) {
            trends.add(
                    Trend.builder()
                            .hashtag(trendDto.getHashtag())
                            .count(trendDto.getCount())
                            .build()
            );
        }

        return trends;
    }

    @Override
    public Trend mapToTrend(String hashtag)
    {
        return Trend.builder()
                .hashtag(hashtag)
                .count(1)
                .build();
    }

    @Override
    public Trend mapToTrend(TrendDto trendDto)
    {
        return Trend.builder()
                .hashtag(trendDto.getHashtag())
                .count(trendDto.getCount())
                .build();
    }
}
