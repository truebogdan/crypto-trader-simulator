package com.ntzk.cryptotradersimulator.template;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ntzk.cryptotradersimulator.MainActivity;
import com.ntzk.cryptotradersimulator.R;
import com.ntzk.cryptotradersimulator.coingecko.CoinGeckoApiClient;
import com.ntzk.cryptotradersimulator.coingecko.constant.Currency;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinFullData;
import com.ntzk.cryptotradersimulator.coingecko.impl.CoinGeckoApiClientImpl;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.MyViewHolder> {
    private Button buyButton;
    private Context ct;
    private Coin[] coins;
    private TextView selectedCoin;
    private TextView selectedPrice;
    private Activity activity;
    private CoinData coindata;
    private EditText amountEditText;
    public CoinAdapter(Context ct, Coin[] coins, Activity activity,CoinData coinData ,TextView selectedCoin, TextView selectedPrice,Button buyButton,EditText amount)
    {
        this.amountEditText=amount;
        this.buyButton=buyButton;
        this.activity=activity;
        this.ct=ct;
        this.coins=coins;
        this.selectedCoin=selectedCoin;
        this.selectedPrice=selectedPrice;
        this.coindata=coinData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.coin_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.coinTicker.setText(coins[position].getTicker());
        holder.nameCoin.setText(coins[position].getName());
        Glide.with(ct).load(coins[position].getImage()).into(holder.coinImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyButton.setEnabled(false);
              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      CoinGeckoApiClient client=new CoinGeckoApiClientImpl();
                      CoinFullData fullData= client.getCoinById(coins[position].getId(),false,false,true,false,false,false);

                      activity.runOnUiThread(new Runnable() {
                          @Override
                          public void run() {

                              selectedCoin.setText(fullData.getName()+"("+fullData.getSymbol().toUpperCase()+")");
                              selectedPrice.setText("$"+fullData.getMarketData().getCurrentPrice().get(Currency.USD).toString());
                              coindata.setName(fullData.getName());
                              coindata.setId(fullData.getId());
                              coindata.setTicker(fullData.getSymbol());
                              coindata.setImage(fullData.getImage().getSmall());
                              coindata.setPrice(fullData.getMarketData().getCurrentPrice().get(Currency.USD));
                              buyButton.setEnabled(true);

                          }
                      });

                  }
              }).start();
            }
        });

    }

    @Override
    public int getItemCount() {
        return coins.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameCoin;
        TextView coinTicker;
        ImageView coinImage;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coinTicker=itemView.findViewById(R.id.coinTickerW);
            nameCoin=itemView.findViewById(R.id.coinNameW);
            coinImage=itemView.findViewById(R.id.coinImage);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }

}
