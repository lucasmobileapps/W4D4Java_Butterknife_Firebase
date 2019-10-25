package com.google.firebase.example.w4d4java_butterknife_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.w4d1api.adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.jvm.JvmStatic;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private List<Message> messageList;

    private String mUsername;
    private String mPhotoUrl;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference myDataReference;

    @BindView(R.id.my_edittext)
    public EditText messageEdittext;
    @BindView(R.id.my_textview)
    public TextView messageTextView;
    @BindView(R.id.my_button)
    public Button addMessage;
    @BindView(R.id.my_recyclerview)
    public RecyclerView myRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }


        //Firebase Database
        ButterKnife.bind(this);

        myDataReference = FirebaseDatabase.getInstance().getReference().child("Message");
        myDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message readMessage = snapshot.getValue(Message.class);
                    Map<String, Message> td = (HashMap<String,Message>) dataSnapshot.getValue();

                    //List<Message> values = td.values();
                    messageTextView.setText(messageTextView.getText()+ "\n" + readMessage.getMessage());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO: Error handle in here
            }
        });

        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message newMessage = new Message(messageEdittext.getText().toString(), mUsername, mPhotoUrl);

                myDataReference.push().setValue(newMessage);
            }
        });


/*
        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter();

 */
    }



}
