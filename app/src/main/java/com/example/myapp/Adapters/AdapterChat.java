package com.example.myapp.Adapters;

import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.ModelChat;
import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {


    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;
    FirebaseUser fUser;

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate layouts: row_chat_left.xml for receiver, row_chat_right for sender
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MyHolder(view);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        String message = chatList.get(position).getMessage();
        String timeStamp = chatList.get(position).getTimestamp();

        //convert time stamp to dd/mm/yyyy hh:mm am/pm
        //Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        //cal.setTimeInMillis(Long.parseLong(timeStamp));
        //String dateTime = DateFormat.getDateTimeInstance().format("dd/MM/YYYY").toString();

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //Date date = new Date();
        //Set data
        holder.messageTv.setText(message);
        //holder.timeTv.setText(date);

        try {

            Picasso.get().load(imageUrl).into(holder.profileIv);
        } catch (Exception e) {


        }

        //set seen/delivered status of message


        if (position == chatList.size() - 1) {

            if (chatList.get(position).isSeen()) {
                holder.isSeenTv.setText("Seen");
            } else {
                holder.isSeenTv.setText("Delivered");
            }

        } else {
            holder.isSeenTv.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //get currently sign in user
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    //View holder class
    class MyHolder extends RecyclerView.ViewHolder {

        //Views
        ImageView profileIv;
        TextView messageTv, timeTv, isSeenTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIv = itemView.findViewById(R.id.profileIv);
            messageTv = itemView.findViewById(R.id.messgeTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);


        }
    }


}
