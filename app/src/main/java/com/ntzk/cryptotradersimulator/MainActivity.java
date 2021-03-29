package com.ntzk.cryptotradersimulator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ntzk.cryptotradersimulator.template.User;


public class MainActivity extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragmentNavHost);
        NavigationUI.setupWithNavController(navigation, navController);
        if(getIntent().getBooleanExtra("newUser",true))
        {
            user=new User(getIntent().getStringExtra("username"),getIntent().getStringExtra("walletUID"),getIntent().getStringExtra("photo"),getIntent().getStringExtra("email"));

        }
        else
        {
            FirebaseFirestore.getInstance().collection("users").document(getIntent().getStringExtra("walletUID")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user=documentSnapshot.toObject(User.class);
                }
            });
        }
    }
    public void updateUser(User user)
    {
        this.user=user;
    }


}