package ru.vez.rx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vez.rx.model.CoinBaseResponse;

@Service
public class CoinBaseServiceImpl implements CoinBaseService {

    public static final String PRICES_URL = "https://api.coinbase.com/v2/prices/{crypoName}/buy";

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<CoinBaseResponse> getCryptoPrice(String priceName) {

        return webClient.get()
                .uri(PRICES_URL, priceName)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CoinBaseResponse.class));
    }
}
