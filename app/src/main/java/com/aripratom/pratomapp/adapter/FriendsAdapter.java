package com.aripratom.pratomapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aripratom.pratomapp.R;
import com.aripratom.pratomapp.activity.FriendsAddActivity;
import com.aripratom.pratomapp.click.CustomOnItemClickListener;
import com.aripratom.pratomapp.entity.Friends;

import java.util.ArrayList;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private final ArrayList<Friends> listFriends = new ArrayList<>();
    private final Activity activity;

    public FriendsAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Friends> getListFriends() {
        return listFriends;
    }

    public void setListFriends(ArrayList<Friends> listFriends) {
        if (listFriends.size() > 0) {
            this.listFriends.clear();
        }
        this.listFriends.addAll(listFriends);
        notifyDataSetChanged();
    }

    public void addItem(Friends friends) {
        this.listFriends.add(friends);
        notifyItemInserted(listFriends.size() - 1);
    }

    public void updateItem(int position, Friends friends) {
        this.listFriends.set(position, friends);
        notifyItemChanged(position, friends);
    }

    public void removeItem(int position) {
        this.listFriends.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listFriends.size());
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.nim.setText(listFriends.get(position).getNim());
        holder.nama.setText(listFriends.get(position).getNama());
        holder.kelas.setText(listFriends.get(position).getKelas());
        holder.telepon.setText(listFriends.get(position).getTelepon());
        holder.email.setText(listFriends.get(position).getEmail());
        holder.sosmed.setText(listFriends.get(position).getSosmed());
        holder.cvFriends.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FriendsAddActivity.class);
                intent.putExtra(FriendsAddActivity.EXTRA_POSITION, position);
                intent.putExtra(FriendsAddActivity.EXTRA_FRIENDS, listFriends.get(position));
                activity.startActivityForResult(intent, FriendsAddActivity.REQUEST_UPDATE);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listFriends.size();
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder {
        final TextView nim, nama, kelas, telepon, email, sosmed;
        final CardView cvFriends;

        FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.tv_item_nim);
            nama = itemView.findViewById(R.id.tv_item_nama);
            kelas = itemView.findViewById(R.id.tv_item_kelas);
            telepon = itemView.findViewById(R.id.tv_item_telepon);
            email = itemView.findViewById(R.id.tv_item_email);
            sosmed = itemView.findViewById(R.id.tv_item_sosmed);
            cvFriends = itemView.findViewById(R.id.cv_item_friends);
        }
    }
}
