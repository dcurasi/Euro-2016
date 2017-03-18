package com.example.dario.euro2016;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/**
 * Created by Dario on 02/07/2016.
 */
public class PartiteAdapter extends RecyclerView.Adapter<PartiteAdapter.ViewHolder> {

    private Cursor cursor;
    private OnItemClickListener listener;

    public PartiteAdapter(Cursor myDataset) {
        this.cursor = myDataset;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView data_partita;
        TextView giornata;
        TextView ora_partita;
        ImageView img_sq_casa;
        TextView slug_sq_casa;
        TextView risultato;
        TextView extra_time;
        TextView slug_sq_fuori;
        ImageView img_sq_fuori;
        LinearLayout rootLayout;

        public ViewHolder(View v) {
            super(v);

            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
            data_partita = (TextView) v.findViewById(R.id.data_partita);
            giornata = (TextView) v.findViewById(R.id.giornata);
            ora_partita = (TextView) v.findViewById(R.id.ora_partita);
            img_sq_casa = (ImageView) v.findViewById(R.id.img_sq_casa);
            slug_sq_casa = (TextView) v.findViewById(R.id.slug_sq_casa);
            risultato = (TextView) v.findViewById(R.id.risultato);
            extra_time = (TextView) v.findViewById(R.id.extra_time);
            slug_sq_fuori = (TextView) v.findViewById(R.id.slug_sq_fuori);
            img_sq_fuori = (ImageView) v.findViewById(R.id.img_sq_fuori);
        }
    }

    @Override
    public PartiteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_partita, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Partita tmp = Utility.cursorToPartita(cursor, position);
        final long id = tmp.getId_api_partita();

        Context context = holder.img_sq_casa.getContext();
        holder.data_partita.setText(tmp.getGiorno());
        holder.giornata.setText(tmp.getGiornataName());
        holder.ora_partita.setText(tmp.getOra());
        Drawable flag_casa = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp.getId_api_casa(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(flag_casa).centerCrop().into(holder.img_sq_casa);
        holder.slug_sq_casa.setText(tmp.getSlugCasa());
        if(tmp.getStato().equals("TIMED")) {
            holder.risultato.setText(" - ");
            holder.extra_time.setText("");
        }
        else {
            holder.risultato.setText(tmp.getRisultato());
            if(tmp.isRigori()) {
                holder.extra_time.setText(tmp.getRisultatoRig());
            }
            else if(tmp.isSupplementari()) {
                holder.extra_time.setText(tmp.getRisultatoSup());
            }
            else {
                holder.extra_time.setText("");
            }
        }
        holder.slug_sq_fuori.setText(tmp.getSlugFuori());
        Drawable flag_fuori = context.getResources()
                .getDrawable(
                        context.getResources().
                                getIdentifier("img_flag_" + tmp.getId_api_fuori(), "drawable", context.getPackageName()));
        Glide.with(context).load("").placeholder(flag_fuori).centerCrop().into(holder.img_sq_fuori);

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onItemClick(id);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClick(long id);
    }

    public void swapCursor(Cursor cursor){

        if(cursor==null){
            this.cursor.close();
        }

        else if(cursor!=this.cursor) {
            this.cursor.close();
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

}