package br.edu.ifam.saf.api.interceptor;


import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

import br.edu.ifam.saf.modelo.Usuario;

@RequestScoped
public class UsuarioAutenticadoProducer {

    @Produces
    @RequestScoped
    @UsuarioAutenticado
    private Usuario usuarioAutenticado;


    public void onEventoAutenticacao(@Observes @UsuarioAutenticado Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

}
