package fhict.kwetter.trendservice.persistence.repository;

import fhict.kwetter.trendservice.persistence.model.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrendRepository extends MongoRepository<Trend, String>
{
    Optional<Trend> findTrendByHashtag(String hashtag);
    boolean existsTrendByHashtag(String hashtag);
}
