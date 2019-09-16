package br.com.joseleles.fiapdesafio.views.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.LeitorDeProperties;
import br.com.joseleles.fiapdesafio.models.Palestra;

public class AdaperPalestras extends RecyclerView.Adapter<AdaperPalestras.PalestraViewHolder> {

    private final Context context;
    private DelegateAdapterOnItemClick<Palestra> onItemClicked;
    private List<Palestra> base;

    public static class PalestraViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imagemPalestra;
        public final TextView titulo,data,hora,vagas,palestrante;
        public final Button verMais;


        public PalestraViewHolder(View view) {
            super(view);
            imagemPalestra = view.findViewById(R.id.imagem_palestra);
            titulo = view.findViewById(R.id.titulo_palestra);
            data = view.findViewById(R.id.data_palestra);
            hora = view.findViewById(R.id.hora_palestra);
            vagas = view.findViewById(R.id.vagas_palestra);
            palestrante = view.findViewById(R.id.palestrante_palestra);

            verMais = view.findViewById(R.id.btn_vermais);
        }
    }

    public AdaperPalestras(Context context, List<Palestra> data, DelegateAdapterOnItemClick<Palestra> onItemClick) {
        this.context = context;
        this.onItemClicked = onItemClick;
        base = (data != null)? new ArrayList<>(data) : new ArrayList<>();
    }

    public PalestraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.palestra_item, parent, false);
        return new PalestraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PalestraViewHolder holder, final int position) {
        Palestra p = base.get(position);
        Picasso.with(context).load(Uri.parse(String.format("%s/Content/Imagens/%s"
                ,new LeitorDeProperties(context).getUrlByPropertie("url_base")
                ,p.getImagem())))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imagemPalestra);
        holder.titulo.setText(p.getTitulo());
        holder.palestrante.setText(String.format("Palestrante: %s", p.getPalestrante()));
        holder.data.setText(String.format("Data: %s", p.getData()));
        holder.hora.setText(String.format("Horário: %s", p.getHora()));
        if(p.getQtdVagasDisponiveis()==0){
            holder.vagas.setTextColor(context.getResources().getColor(R.color.vermelho));
            holder.vagas.setText(context.getString(R.string.vagas_estotadas));
        }else{
            holder.vagas.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            holder.vagas.setText(String.format("Vagas: %s",p.getQtdVagasDisponiveis()));

        }
        holder.verMais.setOnClickListener(view -> {
            onItemClicked.onItemClicked(base.get(position), position);
        });

    }

    @Override
    public int getItemCount() {
        return base.size();
    }


}

