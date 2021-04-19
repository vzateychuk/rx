package ru.vez.rx.cmd;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.vez.rx.observer.ConsolePrintObserver;
import ru.vez.rx.service.CoinBaseService;

import java.util.concurrent.TimeUnit;

@Component
public class CmdLineUi implements CommandLineRunner {

    @Autowired
    private CoinBaseService coinBaseService;

    @Override
    public void run(String... args) throws Exception {

        Observable.interval(3000, TimeUnit.MILLISECONDS, Schedulers.io() )
                .map(
                        tick -> coinBaseService.getCryptoPrice("BTC-USD")
                )
                .subscribe( new ConsolePrintObserver() );

        Observable.interval(3000, TimeUnit.MILLISECONDS, Schedulers.io() )
                .map(
                        tick -> coinBaseService.getCryptoPrice("ETH-USD")
                )
                .subscribe( new ConsolePrintObserver() );

    }

}
