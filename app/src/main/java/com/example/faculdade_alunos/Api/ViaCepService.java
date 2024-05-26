package com.example.faculdade_alunos.Api;

import com.example.faculdade_alunos.Model.Aluno;
import com.example.faculdade_alunos.Model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ViaCepService {
    @GET("{cep}/json/")
    Call<Endereco> getEnderecoByCep(@Path("cep") String cep);
}
