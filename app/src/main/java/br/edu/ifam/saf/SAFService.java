package br.edu.ifam.saf;


import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.ItensAluguelResponse;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.StatusItemAluguelData;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.enums.StatusItemAluguel;
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

    @PUT("itens/{item_id}")
    Observable<Result<Void>> atualizarItem(@Path("item_id") Integer itemId, @Body ItemDTO itemDTO);

    @POST("usuarios")
    Observable<Result<Void>> registrarUsuario(@Body UsuarioDTO usuario);

    @GET("usuarios")
    Observable<Result<UsuariosResponse>> listarUsuarios();

    @GET("usuarios/{usuario_id}")
    Observable<Result<UsuarioDTO>> consultarUsuario(@Path("usuario_id") Integer usuarioId);

    @PUT("usuarios/{usuario_id}")
    Observable<Result<Void>> atualizarUsuario(@Path("usuario_id") Integer usuarioId, @Body UsuarioDTO usuarioDTO);

    @POST("usuarios/login")
    Observable<Result<UsuarioDTO>> login(@Body LoginData loginData);

    @GET("item_aluguel")
    Observable<Result<ItensAluguelResponse>> listarRequisicoes(@Query("status") StatusItemAluguel statusAluguel);

    @POST("alugueis")
    Observable<Result<Void>> cadastrarAluguel(@Body AluguelDTO aluguelDTO);

//    @PUT("alugueis/{aluguel_id}")
//    Observable<Result<Void>> alterarStatus(@Path("aluguel_id") Integer aluguelId, @Body StatusData statusData);

    @GET("categorias")
    Observable<Result<CategoriasResponse>> listarCategorias();

    @POST("categorias")
    Observable<Result<Void>> cadastrarCategorias(@Body CategoriaDTO categoriaDTO);


    @PUT("item_aluguel/{item_aluguel_id}")
    Observable<Result<Void>> atualizaItemAluguelStatus(@Path("item_aluguel_id") Integer itemAluguelId, @Body StatusItemAluguelData status);
}