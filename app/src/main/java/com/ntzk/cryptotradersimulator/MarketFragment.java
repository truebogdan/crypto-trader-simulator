package com.ntzk.cryptotradersimulator;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ntzk.cryptotradersimulator.template.Coin;
import com.ntzk.cryptotradersimulator.template.CoinAdapter;
import com.ntzk.cryptotradersimulator.template.CoinData;
import com.ntzk.cryptotradersimulator.template.User;

import java.util.ArrayList;

public class MarketFragment extends Fragment {

    RecyclerView marketRecView;
    CoinAdapter coinAdapter;
    Button buyButton;
    CoinData selectedData=new CoinData();
    TextView selectedCoin,selectedPrice;
    EditText amountEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ArrayList<Coin> coins = new ArrayList<>();
        String[] coinsName=getResources().getStringArray(R.array.coinsName);
        String[] coinsId = getResources().getStringArray(R.array.coinsId);
        String[] coinsImage=getResources().getStringArray(R.array.coinsImage);
        String [] coinsTicker=getResources().getStringArray(R.array.coinsTicker);
        for(int i=0;i<coinsId.length;i++)
        {
            coins.add(new Coin(coinsName[i],coinsId[i],coinsImage[i],coinsTicker[i]));
        }
        Activity activity=getActivity();

        coinAdapter = new CoinAdapter(getContext(), (coins.toArray(new Coin[0])),activity,selectedData,selectedCoin,selectedPrice,buyButton,amountEdit);

        marketRecView.setAdapter(coinAdapter);
        marketRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        buyButton.setEnabled(false);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountEdit.getText().toString().equals(""))
                {
                    Coin coinToBuy=new Coin(selectedData.getName(),selectedData.getId(),selectedData.getImage(),selectedData.getTicker());
                    User user=((MainActivity)getActivity()).user;
                    boolean successfull=user.buyCoin(coinToBuy,Double.parseDouble(amountEdit.getText().toString()),selectedData.getPrice());
                    if(successfull)
                        Toast.makeText(activity, "Successfull!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(activity, "Something happened wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void initViews()
    {
        marketRecView = getView().findViewById(R.id.marketRecView);
        buyButton=getView().findViewById(R.id.buyButton);
        amountEdit=getView().findViewById(R.id.amountEdit);
        selectedCoin=getView().findViewById(R.id.selectedCoin);
        selectedPrice=getView().findViewById(R.id.selectedPrice);
    }
}

