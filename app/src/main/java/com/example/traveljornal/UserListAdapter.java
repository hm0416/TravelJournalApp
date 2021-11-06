package com.example.traveljornal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljornal.databaseclasses.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;

    public UserListAdapter(MainActivity context) {
        this.context = context;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        holder.tvFullName.setText(this.userList.get(position).FullName);
        holder.tvUserID.setText(this.userList.get(position).id);
        holder.tvEmail.setText(this.userList.get(position).email);
        holder.tvPassword.setText(this.userList.get(position).password);

    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserID;
        TextView tvFullName;
        TextView tvPassword;
        TextView tvEmail;
        public MyViewHolder(View view) {
            super(view);
            tvUserID = view.findViewById(R.id.tvUserID);
            tvFullName= view.findViewById(R.id.tvFullName);
            tvPassword = view.findViewById(R.id.tvPassword);
            tvEmail = view.findViewById(R.id.tvEmail);

        }
    }
}
