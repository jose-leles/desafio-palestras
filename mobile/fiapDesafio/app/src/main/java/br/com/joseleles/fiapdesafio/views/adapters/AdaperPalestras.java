package br.com.joseleles.fiapdesafio.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.models.Palestra;

public class AdaperPalestras extends RecyclerView.Adapter<AdaperPalestras.PalestraViewHolder> {

    private final Context context;
    private DelegateAdapterOnItemClick<Palestra> onItemClicked;
    private List<Palestra> base;

    public void add(Palestra s,int position) {
        position = position == -1 ? getItemCount()  : position;
        base.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            base.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class PalestraViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public PalestraViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titulo_palestra);
        }
    }

    public AdaperPalestras(Context context, List<Palestra> data, DelegateAdapterOnItemClick<Palestra> onItemClick) {
        this.context = context;
        this.onItemClicked = onItemClick;
        if (data != null)
            base = new ArrayList<Palestra>(data);
        else base = new ArrayList<>();
    }

    public PalestraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.palestra_item, parent, false);
        return new PalestraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PalestraViewHolder holder, final int position) {
        holder.title.setText(base.get(position).getTitulo());
        holder.title.setOnClickListener(view -> {
            onItemClicked.onItemClicked(base.get(position), position);
        });

    }

    @Override
    public int getItemCount() {
        return base.size();
    }


}

