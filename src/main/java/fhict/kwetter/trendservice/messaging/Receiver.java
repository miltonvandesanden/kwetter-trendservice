package fhict.kwetter.trendservice.messaging;

import fhict.kwetter.trendservice.logic.AddTrendDelegate;
import fhict.kwetter.trendservice.mappers.TrendMapperDelegate;
import fhict.kwetter.trendservice.messaging.dto.HashtagsDto;
import fhict.kwetter.trendservice.persistence.model.Trend;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Receiver implements RabbitListenerConfigurer
{
    private Optional<TrendMapperDelegate> trendMapperDelegate;

    private Optional<AddTrendDelegate> addTrendDelegate;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar)
    {

    }

    @RabbitListener(queues = "${spring.messaging.queue}")
    public void receivedMessage(HashtagsDto hashtagsDto) throws Exception {
        if(addTrendDelegate.isEmpty()) {
            throw new Exception("AddTrendService not implemented!");
        }

        if(trendMapperDelegate.isEmpty()) {
            throw new Exception("TrendMapper not implemented!");
        }

        List<Trend> trends = trendMapperDelegate.get().mapToTrends(hashtagsDto);

        for(Trend trend : trends) {
            addTrendDelegate.get().addTrend(trend);
        }
    }
}
