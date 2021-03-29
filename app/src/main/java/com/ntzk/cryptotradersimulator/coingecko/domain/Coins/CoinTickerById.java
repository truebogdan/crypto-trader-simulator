package com.ntzk.cryptotradersimulator.coingecko.domain.Coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntzk.cryptotradersimulator.coingecko.domain.Shared.Ticker;
import lombok.*;

import java.util.List;

@Data
public class CoinTickerById {
    @JsonProperty("name")
    private String name;
    @JsonProperty("tickers")
    private List<Ticker> tickers;

}
