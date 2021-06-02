package fhict.kwetter.trendservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Trends")
public class Trend
{
    @Id
    private String hashtag;
    private int count;

    @Override
    public String toString() {
        return String.format(
                "Trend[hashtag=%s, count=%s]",
                hashtag, count
        );
    }
}
