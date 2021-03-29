package com.ntzk.cryptotradersimulator.template;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String walletUID;
    private String photo;
    private String email;
    private double fiat;
    private List<Coin> wallet;

    public User(String name, String walletUID, String photo, String email) {
        this.name = name;
        this.walletUID = walletUID;
        this.photo = photo;
        this.email = email;
        this.wallet = new ArrayList<>();
        fiat=1500;
        FirebaseFirestore.getInstance().collection("users").document(getWalletUID()).set(this);
    }
    public User()
    {
    }

    public double getFiat() {
        return fiat;
    }

    public void setFiat(double fiat) {
        this.fiat = fiat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWalletUID() {
        return walletUID;
    }

    public void setWalletUID(String walletUID) {
        this.walletUID = walletUID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Coin> getWallet() {
        return wallet;
    }

    public void setWallet(List<Coin> wallet) {
        this.wallet = wallet;
    }
    public boolean buyCoin(Coin coin,double amount,double price)
    {
       if(amount*price<=fiat) {
           if (getCoinFromWallet(coin.getName()) != null) {
               getCoinFromWallet(coin.getName()).incrementAmount(amount);
           } else {
               coin.setAmount(amount);
               wallet.add(coin);

           }
           fiat = fiat - price * amount;
           updateDocument();
           return true;
       }
       return false;
    }
    public boolean sellCoin(Coin coin,double amount,double price)
    {
        if(getCoinFromWallet(coin.getName())!=null)
        {
            if(getCoinFromWallet(coin.getName()).getAmount()>=amount)
            {
                fiat=fiat + price*amount;
                getCoinFromWallet(coin.getName()).decrementAmount(amount);
                if(getCoinFromWallet(coin.getName()).getAmount()==0)
                    wallet.remove(getCoinFromWallet(coin.getName()));
                updateDocument();
                return true;
            }
        }
        return false;

    }
    private Coin getCoinFromWallet(String coinName){
        for(Coin c:wallet)
        {
            if(c.getName().equals(coinName))
                return c;
        }
        return null;
    }
    private void updateDocument()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(getWalletUID()).delete();
        db.collection("users").document(getWalletUID()).set(this);
    }
    public boolean transferTo(String destID,String coinName,double amount)
    {
        if(getCoinFromWallet(coinName)!=null)
        {
            if(getCoinFromWallet(coinName).getAmount()>=amount)
            {
                Coin coin=getCoinFromWallet(coinName);
             sellCoin(getCoinFromWallet((coinName)),amount,0);

             FirebaseFirestore.getInstance().collection("users").document(destID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {

                     User destUser=documentSnapshot.toObject(User.class);
                     if(destUser!=null)
                     destUser.buyCoin(coin,amount,0);
                 }
             });
            }
            return true;
        }
        return false;
    }
}
