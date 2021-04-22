package ru.vez.rx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;

@RestController
public class DeferredResultController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @GetMapping("/async")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {

        LOG.info("Received async-deferredresult request");
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>(5L);
        output.onCompletion(() -> LOG.info("Processing complete"));
        output.onTimeout(
                () -> output.setErrorResult(
                        ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred.")
                )
        );
        output.onError((Throwable t) -> {
            output.setErrorResult(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred."));
        });

        ForkJoinPool.commonPool().submit(() -> {
            LOG.info("Processing in separate thread");
            try {
                Thread.sleep(6_000);
            } catch (InterruptedException e) {
            }
            output.setResult(ResponseEntity.ok("ok"));
            LOG.info("Finished processing in separate thread");
            throw new RuntimeException("Something bad happened");
        });

        LOG.info("Servlet thread freed");
        return output;
    }
}
