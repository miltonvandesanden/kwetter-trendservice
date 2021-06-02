package fhict.kwetter.trendservice.messaging;

import fhict.kwetter.trendservice.logic.AddTrendDelegate;
import fhict.kwetter.trendservice.mappers.TrendMapperDelegate;
import fhict.kwetter.trendservice.messaging.dto.HashtagsDto;
import fhict.kwetter.trendservice.persistence.model.Trend;
import fhict.kwetter.trendservice.presentation.controller.AbstractController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class Receiver implements RabbitListenerConfigurer
{
    private Optional<TrendMapperDelegate> trendMapperDelegate;

    private Optional<AddTrendDelegate> addTrendDelegate;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar)
    {

    }

    @RabbitListener(queues = "${spring.messaging.queue}")
    public void receivedMessage(HashtagsDto hashtagsDto) {
        log.info(">> {}", "receivedMessage");

        final long start = System.currentTimeMillis();
        long duration = 0L;

        try {
            handleMessage(hashtagsDto);

            duration = System.currentTimeMillis() - start;
            log.info("<< {} [{} ms]", "receivedMessage", duration);
        } catch (Exception exception)
        {
            duration = System.currentTimeMillis() - start;
            log.warn("<< {} (FAULT) [{} ms]", "receivedMessage", duration);
            exception.printStackTrace();
        }
    }

    private void handleMessage(HashtagsDto hashtagsDto) throws Exception
    {
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
