package com.ntzk.cryptotradersimulator.coingecko.domain.Coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinData.*;
import com.ntzk.cryptotradersimulator.coingecko.domain.Shared.Image;

import lombok.Data;

import java.util.Map;

@Data
public class CoinHistoryById {
    @JsonProperty("id")
    private String id;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("localization")
    private Map<String, String> localization;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("market_data")
    private MarketData marketData;
    @JsonProperty("community_data")
    private CommunityData communityData;
    @JsonProperty("developer_data")
    private DeveloperData developerData;
    @JsonProperty("public_interest_stats")
    private PublicInterestStats publicInterestStats;

}
