package com.ntzk.cryptotradersimulator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ntzk.cryptotradersimulator.template.User;

import net.glxn.qrgen.android.QRCode;


public class ProfileFragment extends Fragment {


    User user;
    ImageView qrCode,profilePicture;
    TextView pName,pMail,pFiat,pQRString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity()!=null)
        {
            init(view);
            user=((MainActivity)getActivity()).user;
            qrCode.setImageBitmap(QRCode.from(user.getWalletUID()).withSize(200,200).bitmap());
            Glide.with(getContext()).load(user.getPhoto()).into(profilePicture);
            pName.setText(user.getName());
            pMail.setText(user.getEmail());
            pFiat.setText("$"+user.getFiat());
            pQRString.setText(user.getWalletUID());
        }

    }
    public void init(View view)
    {
        qrCode = view.findViewById(R.id.QRImageView);
        profilePicture=view.findViewById(R.id.userProfileImage);
        pName=view.findViewById(R.id.userName);
        pMail=view.findViewById(R.id.mailUser);
        pQRString=view.findViewById(R.id.QRString);
        pFiat=view.findViewById(R.id.userFiat);
    }
}