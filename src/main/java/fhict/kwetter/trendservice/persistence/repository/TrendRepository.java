package fhict.kwetter.trendservice.persistence.repository;

import fhict.kwetter.trendservice.persistence.model.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendRepository extends MongoRepository<Trend, String>
{
}
