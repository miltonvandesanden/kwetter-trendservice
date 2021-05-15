package fhict.kwetter.trendservice.logic;

import fhict.kwetter.trendservice.persistence.model.Trend;

public interface GetTrendDelegate
{
    Trend getTrend(String hashtag);
}
