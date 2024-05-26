package com.example.faculdade_alunos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculdade_alunos.Activity.AlunoActivity;
import com.example.faculdade_alunos.Api.AlunoService;
import com.example.faculdade_alunos.Api.ApiClient;
import com.example.faculdade_alunos.Model.Aluno;
import com.example.faculdade_alunos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoHolder> {
    private final List<Aluno> alunos;
    Context context;

    public AlunoAdapter(List<Aluno> alunos, Context context){
        this.alunos = alunos;
        this.context = context;
    }


    @NonNull
    @Override
    public AlunoAdapter.AlunoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlunoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_aluno, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoAdapter.AlunoHolder holder, int position) {
        holder.ra.setText(alunos.get(position).getId() + " - " + alunos.get(position).getRa());
        holder.nome.setText(alunos.get(position).getNome());
        holder.btnexcluir.setOnClickListener(view -> removerItem(position));
        holder.btnEditar.setOnClickListener(view -> editarItem(position));

    }
    private void editarItem(int position) {
        int id = alunos.get(position).getId();
        Intent i = new Intent(context, AlunoActivity.class);
        i.putExtra("id",id);
        context.startActivity(i);
    }

    private void removerItem(int position) {
        int id = alunos.get(position).getId();
        AlunoService apiService = ApiClient.getAlunoService();
        Call<Void> call = apiService.deleteAluno(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // A solicitação foi bem-sucedida
                    alunos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, alunos.size());
                    Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    // A solicitação falhou
                    Log.e("Exclusao","Erro ao excluir");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Ocorreu um erro ao fazer a solicitação
                Log.e("Exclusao","Erro ao excluir");
            }
        });
    }

    @Override
    public int getItemCount() {
        return alunos != null ? alunos.size() : 0;
    }

    public class AlunoHolder extends RecyclerView.ViewHolder{
        public TextView ra;
        public TextView nome;
        public ImageView btnexcluir;
        public ImageView btnEditar;

        public AlunoHolder(View itemView){
            super(itemView);
            ra = (TextView) itemView.findViewById(R.id.txtRa);
            nome = (TextView) itemView.findViewById(R.id.txtNome);
            btnexcluir = (ImageView) itemView.findViewById(R.id.btnExcluir);
            btnEditar = (ImageView) itemView.findViewById(R.id.btnEditar);
        }

    }
}
