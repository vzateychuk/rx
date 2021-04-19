package ru.vez.rx.service;

import reactor.core.publisher.Mono;
import ru.vez.rx.model.CoinBaseResponse;

public interface CoinBaseService {

    Mono<CoinBaseResponse> getCryptoPrice(String priceName);

}
