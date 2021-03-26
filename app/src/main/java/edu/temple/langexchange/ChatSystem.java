package edu.temple.langexchange;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.List;
import java.util.Random;

public class ChatSystem extends AppCompatActivity implements RoomListener {

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "";
    private final String roomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private String userName, targetLang, prefLang, receivedLang = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagingtabgui);

        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
        receivedLang = intent.getStringExtra("langSelected");
        System.out.println(receivedLang);
        channelID = intent.getStringExtra("channelID");
        int userNameController = userName.indexOf("@");
        System.out.println("username received: " + userName);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Account");
        Query query = ref.orderByChild("username").equalTo(userName).limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    targetLang = childSnapshot.child("learnLang").getValue().toString().toUpperCase();
                    prefLang = childSnapshot.child("prefLang").getValue().toString();
                    System.out.println("target lang is: " + targetLang);
                    System.out.println("pref lang is: " + prefLang);
                }
                if(prefLang.toUpperCase().equals(receivedLang))
                {
                    userName = userName.substring(0, userNameController) + " - Native";
                }
                else
                {
                    userName = userName.substring(0, userNameController) + " - Learner";
                }

                if(channelID == "")
                {
                    Toast.makeText(ChatSystem.this, "Unable to Connect to Chat", Toast.LENGTH_LONG).show();
                    finish();
                }

                if(!isFinishing())
                {
                    editText = (EditText) findViewById(R.id.editText);

                    messageAdapter = new MessageAdapter(ChatSystem.this);
                    messagesView = (ListView) findViewById(R.id.messages_view);
                    messagesView.setAdapter(messageAdapter);

                    MemberData data = new MemberData(userName, getRandomColor());
                    messagesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            //View myView = parent.getAdapter().getView(position,null, parent);
                            TextView myTranslation = (TextView) view.findViewById(R.id.translation);
                            TextView original = (TextView) view.findViewById(R.id.message_body);
                            if (myTranslation.getText().toString().isEmpty()){
                                String translateView = original.getText().toString() + "\n\n Translation: " + Translator.translate(original.getText().toString(), prefLang, ChatSystem.this);
                                myTranslation.setText(translateView);
                            }
                            if(myTranslation.getVisibility() == View.INVISIBLE){
                                myTranslation.setVisibility(View.VISIBLE);
                                original.setVisibility(View.INVISIBLE);
                            }
                            else{
                                myTranslation.setVisibility(View.INVISIBLE);
                                original.setVisibility(View.VISIBLE);
                            }
                            return true;
                        }
                    });


                    String welcomeString = "Welcome to " + receivedLang + " Channel";
                    System.out.println(welcomeString);
                    System.out.println("channelID received: " + channelID);
                    System.out.println("targetLang received: " + targetLang);
                    scaledrone = new Scaledrone(channelID, data);
                    Toast.makeText(ChatSystem.this, welcomeString, Toast.LENGTH_LONG).show();
                    scaledrone.connect(new Listener() {
                        @Override
                        public void onOpen() {
                            System.out.println("Scaledrone connection open");
                            scaledrone.subscribe(roomName, ChatSystem.this);
                        }

                        @Override
                        public void onOpenFailure(Exception ex) {
                            System.err.println(ex);
                        }

                        @Override
                        public void onFailure(Exception ex) {
                            System.err.println(ex);
                        }

                        @Override
                        public void onClosed(String reason) {
                            System.err.println(reason);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish(roomName, message);
            editText.getText().clear();
        }
    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}