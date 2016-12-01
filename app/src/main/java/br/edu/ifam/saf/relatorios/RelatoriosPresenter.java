package br.edu.ifam.saf.relatorios;

import android.util.Log;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItemRelatorioResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RelatoriosPresenter implements RelatoriosContract.Presenter {

    private RelatoriosContract.View view;

    private SAFService service;

    public RelatoriosPresenter(RelatoriosContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    void fechMaisAlugados() {
        service.relatorioItensMaisAlugados()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCallback<ItemRelatorioResponse>() {
            @Override
            public void onSuccess(ItemRelatorioResponse response) {
                view.mostrarItensMaisAlugados(response.getItens());
            }

            @Override
            public void onError(MensagemErroResponse mensagem) {
                Log.w("RelatorioPresenter", mensagem.join());

            }

            @Override
            public boolean canExecute() {
                return view != null;
            }
        });

    }

    void fetchMediaItensPorAluguel() {
        service.relatorioItensMaisAlugados()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCallback<ItemRelatorioResponse>() {
            @Override
            public void onSuccess(ItemRelatorioResponse response) {
                view.mostrarMediaDeItensPorAluguel(response.getItens().get(0));
            }

            @Override
            public void onError(MensagemErroResponse mensagem) {
                Log.w("RelatorioPresenter", mensagem.join());

            }

            @Override
            public boolean canExecute() {
                return view != null;
            }
        });

    }

    void fetchUsuarioMaisFrequentes() {
        service.usuariosMaisFrequentes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new ApiCallback<ItemRelatorioResponse>() {
            @Override
            public void onSuccess(ItemRelatorioResponse response) {
                view.mostrarUsuariosMaisFrequentes(response.getItens());
            }

            @Override
            public void onError(MensagemErroResponse mensagem) {
                Log.w("RelatorioPresenter", mensagem.join());

            }

            @Override
            public boolean canExecute() {
                return view != null;
            }
        });

    }

    @Override
    public void start() {
        fechMaisAlugados();
        fetchMediaItensPorAluguel();
        fetchUsuarioMaisFrequentes();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
