package com.ntzk.cryptotradersimulator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ntzk.cryptotradersimulator.template.Coin;
import com.ntzk.cryptotradersimulator.template.CoinData;
import com.ntzk.cryptotradersimulator.template.User;
import com.ntzk.cryptotradersimulator.template.WalletAdapter;
import java.util.List;


public class WalletFragment extends Fragment {

RecyclerView recyclerView;
TextView selectedName,selectedAmount,selectedPrice;
EditText amountEditText;
Button sellButton;
CoinData coinData;
WalletAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        sellButton.setEnabled(false);
        FirebaseFirestore.getInstance().collection("users").document(((MainActivity) getActivity()).user.getWalletUID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(getActivity()!=null)
                {
                    User user =documentSnapshot.toObject(User.class);
                    ((MainActivity)getActivity()).updateUser(user);
                    List<Coin> coins =user.getWallet();
                    adapter=new WalletAdapter(getContext(),coins.toArray(new Coin[0]),selectedName,selectedPrice,selectedAmount,getActivity(),amountEditText,coinData,sellButton);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }


            }
        });
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= ((MainActivity)getActivity()).user;
                Coin tosell = new Coin(coinData.getName(),Double.parseDouble(selectedAmount.getText().toString()));
               boolean successfull= user.sellCoin(tosell,Double.parseDouble(amountEditText.getText().toString()),coinData.getPrice());
                adapter.notifyDataSetChanged();
                if (successfull)
                    Toast.makeText(getContext(), "Successfull!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Something happened wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void init(View view)
    {
        coinData=new CoinData();
        recyclerView=view.findViewById(R.id.walletRecView);
        selectedName=view.findViewById(R.id.selectedWName);
        selectedPrice=view.findViewById(R.id.selectedWPrice);
        selectedAmount=view.findViewById(R.id.selectedWAmount);
        amountEditText=view.findViewById(R.id.editTextAmountWallet);
        sellButton=view.findViewById(R.id.sellButton);
    }
}