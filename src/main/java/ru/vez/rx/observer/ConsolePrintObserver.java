package ru.vez.rx.observer;

import io.reactivex.observers.DefaultObserver;
import reactor.core.publisher.Mono;
import ru.vez.rx.model.CoinBaseResponse;

import java.time.LocalDateTime;

public class ConsolePrintObserver extends DefaultObserver<Mono<CoinBaseResponse>> {

    @Override
    public void onNext(Mono<CoinBaseResponse> mono) {
        mono.subscribe(
                response -> System.out.printf("[%s] buy price: $%s crypto: %s%n",
                        LocalDateTime.now(),
                        response.getData().getAmount(),
                        response.getData().getBase()
                )
        );
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("exception: " + e);
    }

    @Override
    public void onComplete() {
        System.out.println("Complete");
    }
}
