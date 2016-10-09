package br.edu.ifam.saf;


import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface SAFService {
    @GET("itens")
    Observable<Result<ItensResponse>> listarItems();

    @POST("login/cadastrar")
    Observable<Response> registrarUsuario(@Body UsuarioDTO usuario);


}
