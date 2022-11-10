package com.example.withtalk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import javax.security.auth.callback.CallbackHandler;

public class Mainpage extends AppCompatActivity {
    private static final String TAG = "Mainpage"; //태그
    private RecyclerView recyclerView;
    MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;
    String stEmail;
    String stUser;
    FirebaseDatabase database;
    ArrayList<Chat> chatArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        database = FirebaseDatabase.getInstance();
        chatArrayList = new ArrayList<>();

        btnSend=(Button)findViewById(R.id.btnSend);
        etText=(EditText)findViewById(R.id.etText);
        stEmail=getIntent().getStringExtra("email");//로그인에서 email 받아오기
        stUser=getIntent().getStringExtra(("user"));

        recyclerView =(RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"test1","test2","test3","test4","test5"};
        mAdapter = new MyAdapter(chatArrayList,stEmail,stUser);
        recyclerView.setAdapter(mAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot,String previousChildName) {
                Log.d(TAG, "onChildAdded:"+ dataSnapshot.getKey());
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String stEmail = chat.getEmail();
                String stText = chat.getText();
                String stUser = chat.getUser();
                Log.d(TAG,"stEmail: "+stEmail);
                Log.d(TAG,"stText: "+stText);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot,String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG,"onChildRemoved:"+dataSnapshot.getKey());
                String commentKey =dataSnapshot.getKey();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot,String previousChildName) {
                Log.d(TAG,"onChildMoved:"+dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"postComments:onCancelled", databaseError.toException());
                Toast.makeText(Mainpage.this, "Failed to load comments.",Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference ref = database.getReference("message");
        ref.addChildEventListener(childEventListener);

        //전송버튼
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stText = etText.getText().toString();

                //날짜밑에 데이터 쌓기
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss"); //초단위까지
                String datetime = dataformat.format(c.getTime());
                DatabaseReference myRef = database.getReference("message").child(datetime);

                Hashtable<String,String> numbers = new Hashtable<String,String>();
                numbers.put("email",stEmail);
                numbers.put("user",stUser);
                numbers.put("text",stText);

                myRef.setValue(numbers);
            }
        });
    }
}
