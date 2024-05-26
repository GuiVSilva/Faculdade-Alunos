package com.example.faculdade_alunos.Api;
import com.example.faculdade_alunos.Model.Aluno;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {

    @GET("users")
    Call<List<Aluno>> getAlunos();
    @POST("users")
    Call<Aluno> postAluno(@Body Aluno aluno);
    @DELETE("users/{id}")
    Call<Void> deleteAluno(@Path("id") int idAluno);
    @GET("users/{id}")
    Call<Aluno> getAlunoPorId(@Path("id") int idAluno);
    @PUT("users/{id}")
    Call<Aluno> putAluno(@Path("id") int idAluno, @Body Aluno aluno);
}
