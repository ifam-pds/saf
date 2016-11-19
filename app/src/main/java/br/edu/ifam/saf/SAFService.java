package br.edu.ifam.saf;


import br.edu.ifam.saf.api.data.AlugueisResponse;
import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.StatusData;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.enums.StatusAluguel;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface SAFService {
    @GET("itens")
    Observable<Result<ItensResponse>> listarItems();

    @POST("itens")
    Observable<Result<Void>> registrarItem(@Body ItemDTO item);

    @GET("itens/{item_id}")
    Observable<Result<ItemDTO>> consultarItem(@Path("item_id") Integer itemId);

    @POST("usuarios")
    Observable<Result<Void>> registrarUsuario(@Body UsuarioDTO usuario);

    @GET("usuarios")
    Observable<Result<UsuariosResponse>> listarUsuarios();

    @POST("usuarios/login")
    Observable<Result<UsuarioDTO>> login(@Body LoginData loginData);

    @GET("alugueis")
    Observable<Result<AlugueisResponse>> alugueis(@Query("status") StatusAluguel statusAluguel);

    @POST("alugueis")
    Observable<Result<Void>> cadastrarAluguel(@Body AluguelDTO aluguelDTO);

    @PUT("alugueis/{aluguel_id}")
    Observable<Result<Void>> alterarStatus(@Path("aluguel_id") Integer aluguelId, @Body StatusData statusData);

    @GET("categorias")
    Observable<Result<CategoriasResponse>> listarCategorias();
}