package com.example.faculdade_alunos.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.faculdade_alunos.Api.AlunoService;
import com.example.faculdade_alunos.Api.ApiClient;
import com.example.faculdade_alunos.Api.ViaCepService;
import com.example.faculdade_alunos.Model.Aluno;
import com.example.faculdade_alunos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoActivity extends AppCompatActivity {

    Button btnSalvar;
    AlunoService apiService;
    TextView txtra, txtnome, txtcep, txtlogradouro, txtcomplemento, txtbairro, txtcidade, txtuf;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aluno);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        apiService = ApiClient.getAlunoService();
        txtra = findViewById(R.id.txtRaAluno);
        txtnome = findViewById(R.id.txtNomeAluno);
        txtcep = findViewById(R.id.txtCepAluno);
        txtlogradouro = findViewById(R.id.txtLogradouroAluno);
        txtcomplemento = findViewById(R.id.txtComplementoAluno);
        txtbairro = findViewById(R.id.txtBairroAluno);
        txtcidade = findViewById(R.id.txtCidadeAluno);
        txtuf = findViewById(R.id.txtUfAluno);
        id = getIntent().getIntExtra("id",0);

        if(id > 0){
            apiService.getAlunoPorId(id).enqueue(new Callback<Aluno>() {
                @Override
                public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                    if(response.isSuccessful()){
                        txtra.setText(response.body().getRa());
                        txtnome.setText(response.body().getNome());
                        txtcep.setText(response.body().getCep());
                        txtlogradouro.setText(response.body().getLogradouro());
                        txtcomplemento.setText(response.body().getComplemento());
                        txtbairro.setText(response.body().getBairro());
                        txtcidade.setText(response.body().getCidade());
                        txtuf.setText(response.body().getUf());
                    }
                }

                @Override
                public void onFailure(Call<Aluno> call, Throwable t) {
                    Log.e("Obter aluno","Erro ao obter aluno");
                }
            });
        }


        btnSalvar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Aluno aluno = new Aluno();
                aluno.setRa(Integer.parseInt(txtra.getText().toString()));
                aluno.setNome(txtnome.getText().toString());
                aluno.setCep(txtcep.getText().toString());
                aluno.setLogradouro(txtlogradouro.getText().toString());
                aluno.setComplemento(txtcomplemento.getText().toString());
                aluno.setBairro(txtbairro.getText().toString());
                aluno.setCidade(txtcidade.getText().toString());
                aluno.setUf(txtuf.getText().toString());
                if(id == 0)
                    inserirAluno(aluno);
                else {
                    aluno.setId(id);
                    editarAluno(aluno);
                }
            }

            private void inserirAluno(Aluno aluno){
                Call<Aluno> call = apiService.postAluno(aluno);
                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                        if(response.isSuccessful()){
                            Aluno createdPost = response.body();
                            Toast.makeText(AlunoActivity.this, "Inserido com Sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Log.e("Inserir", "Erro ao criar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Aluno> call, Throwable t) {
                        Log.e("Inserir", "Erro ao criar: " + t.getMessage());
                    }
                });
            }

            private void editarAluno(Aluno aluno){
                Call<Aluno> call = apiService.putAluno(id, aluno);
                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                        if(response.isSuccessful()){
                            Aluno createdPut = response.body();
                            Toast.makeText(AlunoActivity.this, "Editado com Sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Log.e("Editar", "Erro ao editar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Aluno> call, Throwable t) {
                        Log.e("Editar", "Erro ao editar: " + t.getMessage());
                    }
                });
            }
        });
    }


}