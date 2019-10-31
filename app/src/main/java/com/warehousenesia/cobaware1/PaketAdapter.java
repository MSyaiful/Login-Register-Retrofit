package com.warehousenesia.cobaware1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.cobaware1.Model.DataPaket;

import java.util.ArrayList;

public class PaketAdapter  extends RecyclerView.Adapter<PaketAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataPaket> PaketList;

    public PaketAdapter(Context context, ArrayList<DataPaket> PaketList){
        inflater = LayoutInflater.from(context);
        this.PaketList = PaketList;
    }

    @NonNull
    @Override
    public PaketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_paket, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.nama_pack.setText(PaketList.get(position).getNama());
        holder.harga_pack.setText(PaketList.get(position).getPrice());
        holder.det_pack1.setText(PaketList.get(position).getDetail1());
        holder.det_pack2.setText(PaketList.get(position).getDetail2());
        holder.det_pack3.setText(PaketList.get(position).getDetail3());
        holder.det_pack4.setText(PaketList.get(position).getDetail4());
        holder.det_pack5.setText(PaketList.get(position).getDetail5());
    }

    @Override
    public int getItemCount() {return PaketList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama_pack, harga_pack, det_pack1, det_pack2, det_pack3, det_pack4, det_pack5;
        CardView card__pack;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nama_pack = itemView.findViewById(R.id.nama_paket);
            harga_pack = itemView.findViewById(R.id.harga_paket);
            det_pack1 = itemView.findViewById(R.id.detail1);
            det_pack2 = itemView.findViewById(R.id.detail2);
            det_pack3 = itemView.findViewById(R.id.detail3);
            det_pack4 = itemView.findViewById(R.id.detail4);
            det_pack5 = itemView.findViewById(R.id.detail5);
            card__pack = itemView.findViewById(R.id.paket_member);
        }
    }
}
