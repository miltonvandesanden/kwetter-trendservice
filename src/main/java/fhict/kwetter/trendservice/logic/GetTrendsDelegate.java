package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;

import java.util.List;

public interface GetTrendsDelegate
{
    List<Trend> getTrends();
}
