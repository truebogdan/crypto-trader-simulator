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
import com.ntzk.cryptotradersimulator.R;
import com.ntzk.cryptotradersimulator.coingecko.CoinGeckoApiClient;
import com.ntzk.cryptotradersimulator.coingecko.constant.Currency;
import com.ntzk.cryptotradersimulator.coingecko.domain.Coins.CoinFullData;
import com.ntzk.cryptotradersimulator.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.Map;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyHolderView> {
    private Context ct;
    private Coin[] coins;
    private CoinData coinData;
    private TextView selectedName,selectedPrice,selectedAmount;
    private Activity activity;
    private EditText amountEt;
    private Button sellButton;
    public WalletAdapter(Context ct, Coin[] coins, TextView selectedName, TextView selectedPrice, TextView selectedAmount, Activity activity, EditText amountEd,CoinData coinData,Button sellButton)
    {
        this.sellButton=sellButton;
        this.ct=ct;
        this.coins=coins;
        this.selectedName=selectedName;
        this.selectedAmount=selectedAmount;
        this.selectedPrice=selectedPrice;
        this.activity=activity;
        this.amountEt=amountEd;
        this.coinData=coinData;

    }
    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.wallet_list,parent,false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        holder.coinName.setText(coins[position].getName());
        holder.coinTicker.setText(coins[position].getTicker());
        holder.amountCoin.setText(" "+coins[position].getAmount());
        Glide.with(ct).load(coins[position].getImage()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sellButton.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CoinGeckoApiClient client=new CoinGeckoApiClientImpl();
                        Double price=client.getPrice(coins[position].getId(),Currency.USD).get(coins[position].getId()).get(Currency.USD);
                        coinData.setName(coins[position].getName());
                        coinData.setPrice(price);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                selectedName.setText(coins[position].getName()+"("+coins[position].getTicker()+")");
                                selectedPrice.setText("$"+price);
                                selectedAmount.setText(""+coins[position].getAmount());
                                amountEt.setText(""+coins[position].getAmount());
                                sellButton.setEnabled(true);
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

    public class MyHolderView extends  RecyclerView.ViewHolder {
        TextView coinName;
        TextView coinTicker;
        TextView amountCoin;
        ImageView imageView;
        CardView cardView;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);
            coinName=itemView.findViewById(R.id.coinNameW);
            coinTicker=itemView.findViewById(R.id.coinTickerW);
            amountCoin=itemView.findViewById(R.id.amountW);
            imageView=itemView.findViewById(R.id.imageW);
            cardView=itemView.findViewById(R.id.cardWView);

        }
    }
}
