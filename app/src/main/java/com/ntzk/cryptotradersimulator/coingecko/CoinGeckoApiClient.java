package com.ntzk.cryptotradersimulator.coingecko;

import com.ntzk.cryptotradersimulator.coingecko.domain.*;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.*;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.EventCountries;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.EventTypes;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.Events;
import com.ntzk.cryptotradersimulator.coingecko.domain.ExchangeRates.ExchangeRates;
import com.ntzk.cryptotradersimulator.coingecko.domain.Exchanges.*;
import com.ntzk.cryptotradersimulator.coingecko.domain.Global.Global;
import com.ntzk.cryptotradersimulator.coingecko.domain.Status.StatusUpdates;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinFullData;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinHistoryById;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinList;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinMarkets;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinTickerById;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.MarketChart;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.EventCountries;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.EventTypes;
import com.ntzk.cryptotradersimulator.coingecko.domain.Events.Events;
import com.ntzk.cryptotradersimulator.coingecko.domain.ExchangeRates.ExchangeRates;
import com.ntzk.cryptotradersimulator.coingecko.domain.Exchanges.ExchangeById;
import com.ntzk.cryptotradersimulator.coingecko.domain.Exchanges.Exchanges;
import com.ntzk.cryptotradersimulator.coingecko.domain.Exchanges.ExchangesList;
import com.ntzk.cryptotradersimulator.coingecko.domain.Exchanges.ExchangesTickersById;
import com.ntzk.cryptotradersimulator.coingecko.domain.Global.Global;
import com.ntzk.cryptotradersimulator.coingecko.domain.Ping;
import com.ntzk.cryptotradersimulator.coingecko.domain.Status.StatusUpdates;

import java.util.List;
import java.util.Map;

public interface CoinGeckoApiClient {
    Ping ping();

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies);

    Map<String, Map<String, Double>> getPrice(String ids, String vsCurrencies, boolean includeMarketCap, boolean include24hrVol,
                                              boolean include24hrChange, boolean includeLastUpdatedAt);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies);

    Map<String, Map<String, Double>> getTokenPrice(String id, String contractAddress, String vsCurrencies, boolean includeMarketCap,
                                                   boolean include24hrVol, boolean include24hrChange, boolean includeLastUpdatedAt);

    List<String> getSupportedVsCurrencies();

    List<CoinList> getCoinList();

    List<CoinMarkets> getCoinMarkets(String vsCurrency);

    List<CoinMarkets> getCoinMarkets(String vsCurrency, String ids, String order, Integer perPage, Integer page, boolean sparkline, String priceChangePercentage);

    CoinFullData getCoinById(String id);

    CoinFullData getCoinById(String id, boolean localization, boolean tickers, boolean marketData, boolean communityData, boolean developerData, boolean sparkline);

    CoinTickerById getCoinTickerById(String id);

    CoinTickerById getCoinTickerById(String id, String exchangeIds, Integer page, String order);

    CoinHistoryById getCoinHistoryById(String id, String date);

    CoinHistoryById getCoinHistoryById(String id, String data, boolean localization);

    MarketChart getCoinMarketChartById(String id, String vsCurrency, Integer days);

    MarketChart getCoinMarketChartRangeById(String id, String vsCurrency, String from, String to);

    StatusUpdates getCoinStatusUpdateById(String id);

    StatusUpdates getCoinStatusUpdateById(String id, Integer perPage, Integer page);

    CoinFullData getCoinInfoByContractAddress(String id, String contractAddress);

    List<Exchanges> getExchanges();

    List<ExchangesList> getExchangesList();

    ExchangeById getExchangesById(String id);

    ExchangesTickersById getExchangesTickersById(String id);

    ExchangesTickersById getExchangesTickersById(String id, String coinIds, Integer page, String order);

    StatusUpdates getExchangesStatusUpdatesById(String id);

    StatusUpdates getExchangesStatusUpdatesById(String id, Integer perPage, Integer page);

    List<List<String>> getExchangesVolumeChart(String id, Integer days);

    StatusUpdates getStatusUpdates();

    StatusUpdates getStatusUpdates(String category, String projectType, Integer perPage, Integer page);

    Events getEvents();

    Events getEvents(String countryCode, String type, Integer page, boolean upcomingEventsOnly, String fromDate, String toDate);

    EventCountries getEventsCountries();

    EventTypes getEventsTypes();

    ExchangeRates getExchangeRates();

    Global getGlobal();
}
