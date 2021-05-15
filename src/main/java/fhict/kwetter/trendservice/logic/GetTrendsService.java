package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;

import fhict.kwetter.trendservice.persistence.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetTrendsService implements GetTrendsDelegate {
    @Autowired
    private final TrendRepository trendRepository;

    @Override
    public List<Trend> getTrends()
    {
        return trendRepository.findAll();
    }
}
