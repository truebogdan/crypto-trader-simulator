package com.ntzk.cryptotradersimulator;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.ntzk.cryptotradersimulator.template.Coin;
import com.ntzk.cryptotradersimulator.template.User;

import java.util.ArrayList;

public class TransferFragment extends Fragment {
    private static final int REQUEST_CODE = 404;
    private TextView destinationAdress;
    private Spinner spinner;
    private User user;
    private String selectedCoin;
    private EditText amountEdit;
    private View root;
    private  Activity activity;

private CodeScanner mCodeScanner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            activity = getActivity();

        root = inflater.inflate(R.layout.fragment_transfer, container, false);
       if( ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
       {
        showHomeFragment();
       }
       else
        requestPermissions(new String[] {Manifest.permission.CAMERA},0);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCodeScanner!=null)
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCodeScanner!=null)
        mCodeScanner.releaseResources();
    }
    public void init(View view)
    {
        user = ((MainActivity)getActivity()).user;
        destinationAdress=view.findViewById(R.id.destinationAdress);
        spinner =view.findViewById(R.id.transferSpinner);
        amountEdit=view.findViewById(R.id.amountEditTransfer);
        Button transferButton = view.findViewById(R.id.transferButton);
        ArrayList<String> coins=new ArrayList<>();
        for(Coin c : user.getWallet())
        {
            coins.add(c.getName());
        }
        if(coins.size()==0)
        {
            coins.add("Your wallet is empty");
            amountEdit.setVisibility(View.INVISIBLE);
            transferButton.setVisibility(View.INVISIBLE);
        }

        String []coinsArray=  coins.toArray(new String[0]);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, coinsArray);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedCoin=coinsArray[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedCoin.equals("Your wallet is empty") && !amountEdit.getText().toString().equals(""))
                {
                 boolean successfull=user.transferTo(destinationAdress.getText().toString(),selectedCoin,Double.parseDouble(amountEdit.getText().toString()));
                 if(successfull)
                     Toast.makeText(getActivity(), "Successfull!", Toast.LENGTH_SHORT).show();
                 else
                     Toast.makeText(getActivity(), "Something happened wrong!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), "Something happened wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showHomeFragment();
            }
        }
    }

    private void showHomeFragment() {
        init(root);
        CodeScannerView scannerView = root.findViewById(R.id.scannerView);
        mCodeScanner=new CodeScanner(activity,scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        destinationAdress.setText(""+result.getText());
                        spinner.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });

    }
}
