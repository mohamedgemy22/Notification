package com.enggemy22.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String Channel_ID = "Simplify_Coding";
    public static final String Channel_Name = "Simplify Coding";
    public static final String Channel_Descreption = "Simplify_Coding Notfication";
    @BindView(R.id.linrear_txt)
    LinearLayout linrearTxt;
    @BindView(R.id.useer_name)
    EditText useerName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.logIn)
    Button logIn;
    @BindView(R.id.prodress)
    ProgressBar prodress;
    @BindView(R.id.linrear_edittxt)
    LinearLayout linrearEdittxt;
    @BindView(R.id.create_Account)
    TextView createAccount;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                   //saveToken(token);

                } else {

                }

            }
        });
        notificationChannel();
        prodress.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        displayNotification();
        onViewClicked();

    }


    public void displayNotification() {

        //Notification Biulder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, Channel_ID)
                        .setSmallIcon(R.drawable.ic_message_black_24dp)
                        .setContentTitle("mohamed")
                        .setContentText("Gemy")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        //Notification Manager

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());

    }

    //Notification Channel
    private void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Channel_ID, Channel_Name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(Channel_Descreption);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    @OnClick(R.id.logIn)
    public void onViewClicked() {
        userData();
    }

    private void userData() {
        String Email, pass;
        Email = useerName.getText().toString().trim();
        pass = password.getText().toString().trim();
        if (Email.isEmpty()) {
            useerName.setError("Email Required");
            useerName.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("password Required");
            password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                userLogin(Email, pass);
                            } else {
                                prodress.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });

    }

    private void userLogin(String email, String passwordddd) {
        mAuth.signInWithEmailAndPassword(email, passwordddd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else {
                    prodress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



//    private void saveToken(String token) {
//
//        String email = mAuth.getCurrentUser().getEmail();
//        users u = new users(email, token);
//        myRef = FirebaseDatabase.getInstance().getReference("user");
//        myRef.child(mAuth.getCurrentUser().getUid())
//                .setValue(u)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                });
//    }
}
