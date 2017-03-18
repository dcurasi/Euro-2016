package com.example.dario.euro2016;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 18/07/2016.
 */
public class ClassificheAdapter extends RecyclerView.Adapter<ClassificheAdapter.ViewHolder> {

    private List<Classifica[]> classifiche;

    public ClassificheAdapter(List<Classifica[]> myDataset) {
        this.classifiche = myDataset;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome_gruppo;
        ImageView img_sq_1;
        TextView nome_sq_1;
        TextView gior_1;
        TextView pti_1;
        TextView gol_fatti_1;
        TextView gol_sub_1;
        TextView diff_reti_1;
        ImageView img_sq_2;
        TextView nome_sq_2;
        TextView gior_2;
        TextView pti_2;
        TextView gol_fatti_2;
        TextView gol_sub_2;
        TextView diff_reti_2;
        ImageView img_sq_3;
        TextView nome_sq_3;
        TextView gior_3;
        TextView pti_3;
        TextView gol_fatti_3;
        TextView gol_sub_3;
        TextView diff_reti_3;
        ImageView img_sq_4;
        TextView nome_sq_4;
        TextView gior_4;
        TextView pti_4;
        TextView gol_fatti_4;
        TextView gol_sub_4;
        TextView diff_reti_4;
        LinearLayout rootClassifica;

        public ViewHolder(View v) {
            super(v);

            rootClassifica = (LinearLayout) itemView.findViewById(R.id.rootClassifica);
            nome_gruppo = (TextView) v.findViewById(R.id.nome_gruppo);
            img_sq_1 = (ImageView) v.findViewById(R.id.cl_img_sq_1);
            nome_sq_1 = (TextView) v.findViewById(R.id.cl_nome_sq_1);
            gior_1 = (TextView) v.findViewById(R.id.cl_g_1);
            pti_1 = (TextView) v.findViewById(R.id.cl_pti_1);
            gol_fatti_1 = (TextView) v.findViewById(R.id.cl_gf_1);
            gol_sub_1 = (TextView) v.findViewById(R.id.cl_gs_1);
            diff_reti_1 = (TextView) v.findViewById(R.id.cl_df_1);
            img_sq_2 = (ImageView) v.findViewById(R.id.cl_img_sq_2);
            nome_sq_2 = (TextView) v.findViewById(R.id.cl_nome_sq_2);
            gior_2 = (TextView) v.findViewById(R.id.cl_g_2);
            pti_2 = (TextView) v.findViewById(R.id.cl_pti_2);
            gol_fatti_2 = (TextView) v.findViewById(R.id.cl_gf_2);
            gol_sub_2 = (TextView) v.findViewById(R.id.cl_gs_2);
            diff_reti_2 = (TextView) v.findViewById(R.id.cl_df_2);
            img_sq_3 = (ImageView) v.findViewById(R.id.cl_img_sq_3);
            nome_sq_3 = (TextView) v.findViewById(R.id.cl_nome_sq_3);
            gior_3 = (TextView) v.findViewById(R.id.cl_g_3);
            pti_3 = (TextView) v.findViewById(R.id.cl_pti_3);
            gol_fatti_3 = (TextView) v.findViewById(R.id.cl_gf_3);
            gol_sub_3 = (TextView) v.findViewById(R.id.cl_gs_3);
            diff_reti_3 = (TextView) v.findViewById(R.id.cl_df_3);
            img_sq_4 = (ImageView) v.findViewById(R.id.cl_img_sq_4);
            nome_sq_4 = (TextView) v.findViewById(R.id.cl_nome_sq_4);
            gior_4 = (TextView) v.findViewById(R.id.cl_g_4);
            pti_4 = (TextView) v.findViewById(R.id.cl_pti_4);
            gol_fatti_4 = (TextView) v.findViewById(R.id.cl_gf_4);
            gol_sub_4 = (TextView) v.findViewById(R.id.cl_gs_4);
            diff_reti_4 = (TextView) v.findViewById(R.id.cl_df_4);
        }
    }

    @Override
    public ClassificheAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_classifica, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        Classifica[] tmp = classifiche.get(position);

        Context context = holder.nome_gruppo.getContext();
        holder.nome_gruppo.setText(tmp[0].getGruppo());
        Drawable img_sq_1 = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp[0].getId_api_sq(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(img_sq_1).centerCrop().into(holder.img_sq_1);
        holder.nome_sq_1.setText(tmp[0].getNome_sq());
        holder.gior_1.setText(String.valueOf(tmp[0].getNum_partite()));
        holder.pti_1.setText(String.valueOf(tmp[0].getPunti()));
        holder.gol_fatti_1.setText(String.valueOf(tmp[0].getGol_fatti()));
        holder.gol_sub_1.setText(String.valueOf(tmp[0].getGol_subiti()));
        holder.gol_sub_1.setText(String.valueOf(tmp[0].getGol_subiti()));
        holder.diff_reti_1.setText(String.valueOf(tmp[0].getDiff_reti()));

        Drawable img_sq_2 = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp[1].getId_api_sq(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(img_sq_2).centerCrop().into(holder.img_sq_2);
        holder.nome_sq_2.setText(tmp[1].getNome_sq());
        holder.gior_2.setText(String.valueOf(tmp[1].getNum_partite()));
        holder.pti_2.setText(String.valueOf(tmp[1].getPunti()));
        holder.gol_fatti_2.setText(String.valueOf(tmp[1].getGol_fatti()));
        holder.gol_sub_2.setText(String.valueOf(tmp[1].getGol_subiti()));
        holder.gol_sub_2.setText(String.valueOf(tmp[1].getGol_subiti()));
        holder.diff_reti_2.setText(String.valueOf(tmp[1].getDiff_reti()));

        Drawable img_sq_3 = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp[2].getId_api_sq(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(img_sq_3).centerCrop().into(holder.img_sq_3);
        holder.nome_sq_3.setText(tmp[2].getNome_sq());
        holder.gior_3.setText(String.valueOf(tmp[2].getNum_partite()));
        holder.pti_3.setText(String.valueOf(tmp[2].getPunti()));
        holder.gol_fatti_3.setText(String.valueOf(tmp[2].getGol_fatti()));
        holder.gol_sub_3.setText(String.valueOf(tmp[2].getGol_subiti()));
        holder.gol_sub_3.setText(String.valueOf(tmp[2].getGol_subiti()));
        holder.diff_reti_3.setText(String.valueOf(tmp[2].getDiff_reti()));

        Drawable img_sq_4 = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp[3].getId_api_sq(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(img_sq_4).centerCrop().into(holder.img_sq_4);
        holder.nome_sq_4.setText(tmp[3].getNome_sq());
        holder.gior_4.setText(String.valueOf(tmp[3].getNum_partite()));
        holder.pti_4.setText(String.valueOf(tmp[3].getPunti()));
        holder.gol_fatti_4.setText(String.valueOf(tmp[3].getGol_fatti()));
        holder.gol_sub_4.setText(String.valueOf(tmp[3].getGol_subiti()));
        holder.gol_sub_4.setText(String.valueOf(tmp[3].getGol_subiti()));
        holder.diff_reti_4.setText(String.valueOf(tmp[3].getDiff_reti()));

    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return classifiche.size();
    }

    public void modelChanged(List<Classifica[]> classifiche){

        this.classifiche = classifiche;
        notifyDataSetChanged();

    }

}
