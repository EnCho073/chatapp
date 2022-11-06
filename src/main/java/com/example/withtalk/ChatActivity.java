package com.example.withtalk;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class
ChatActivity extends AppCompatActivity {

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private List<ChatData> chatList;
        private String nick = "nick2"; // 1:1 or 1:da로

        private EditText EditText_chat;
        private Button Button_send;
        private DatabaseReference myRef;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_chat);

                Button_send = findViewById(R.id.Button_send);
                EditText_chat = findViewById(R.id.EditText_chat);

                //샌드 눌렀을떄
                Button_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                String msg = EditText_chat.getText().toString(); //msg
                                //널이 아닐때만 값전송하게
                                if(msg != null){
                                        ChatData chat = new ChatData();
                                        chat.setNickname(nick);
                                        chat.setMsg(msg);
                                        myRef.push().setValue(chat); //setValue(chat)에서 수정 push() 붙였음
                                }

                        }
                });

                mRecyclerView = findViewById(R.id.my_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(mLayoutManager);


                chatList = new ArrayList<>();
                //어뎁터



                // Write a message to the database
                //database 선언과 생성
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //message를 참조(getReference)해서 가져옴.
                myRef = database.getReference();

       /* ChatData chat = new ChatData();
        chat.setNickname(nick);
        chat.setMsg("hi");
        myRef.setValue(chat);*/
                //주의사항


                myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                });
                //0.채팅 앱 만들기
                //1. recycleView - 어떤 데이터를 반복해 보여주는 용도로 많이씀.
                //2. 데이터베이서 내용을 넣는다.
                //3. 상대방폰에 채팅 내용이 보이게.

                //1-1. recycleview - chat data
                //1. message, nickname, ismine - Data Transfer Object(데이터를 교환하는 객체)
        }
}


