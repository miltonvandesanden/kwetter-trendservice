package fhict.kwetter.trendservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trend
{
    @Id
    public String id;

    private String hashtag;
    private int count;

    @Override
    public String toString() {
        return String.format(
                "Trend[id=%s, hashtag=%s, count=%s]",
                id, hashtag, count
        );
    }
}
