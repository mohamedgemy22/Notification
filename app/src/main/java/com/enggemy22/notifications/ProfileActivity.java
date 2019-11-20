package com.enggemy22.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        firebseCloudMessging();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private void firebseCloudMessging() {
        
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
              saveToken(token);
                } else {

                }

            }
        });
    }

    private void saveToken(String token) {
        String mail= mAuth.getCurrentUser().getEmail();
        users use=new users(mail,token);

        myRef=FirebaseDatabase.getInstance().getReference("notes")
                .child(mAuth.getCurrentUser().getUid())
                .push();
                myRef.setValue(use).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if (task.isSuccessful()){
               Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_LONG).show();
               }else{
               Toast.makeText(getApplicationContext()," not saved",Toast.LENGTH_LONG).show();

           }
            }
        });
    }


}
