package fhict.kwetter.trendservice.presentation.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public abstract class AbstractController
{
    <T> T call(String operation, Supplier<T> supplier) {
        log.info(">> {}", operation);

        final long start = System.currentTimeMillis();
        long duration = 0L;

        try
        {
            T returnValue = supplier.get();

            duration = System.currentTimeMillis() - start;
            log.info("<< {} [{} ms]", operation, duration);

            return returnValue;
        } catch (Throwable throwable) {
            duration = System.currentTimeMillis() - start;
            log.warn("<< {} (FAULT) [{} ms]", operation, duration);

            throw throwable;
        }
    }
}
