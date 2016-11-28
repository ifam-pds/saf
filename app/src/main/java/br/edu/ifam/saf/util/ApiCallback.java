package br.edu.ifam.saf.util;


import br.edu.ifam.saf.api.data.MensagemErroResponse;
import retrofit2.adapter.rxjava.Result;
import rx.functions.Action1;

public abstract class ApiCallback<E> implements Action1<Result<E>> {
    @Override
    public void call(Result<E> result) {
        if (canExecute()) {
            if (result.isError()) {
                result.error().printStackTrace();
                onException(result.error());
            } else if (result.response().isSuccessful()) {
                onSuccess(result.response().body());
            } else {
                MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                onError(mensagem);
            }

        }

    }


    public abstract void onSuccess(E response);

    public abstract void onError(MensagemErroResponse mensagem);

    public abstract boolean canExecute();

    public void onException(Throwable e) {
        onError(new MensagemErroResponse("Erro desconhecido: " + e.getMessage()));
    }


}
