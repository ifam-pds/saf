package br.edu.ifam.saf.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.exception.ValidacaoError;

public final class Validation {

    private static final String EMAIL_INVALIDO = "Email inválido";


    public static void validaLogin(LoginData loginData) throws ValidacaoError {
        if (StringUtils.isAnyBlank(loginData.getEmail(), loginData.getSenha())) {
            throw new ValidacaoError("Todos os campos são obrigatórios");
        }

    }

    public static void validaRegistroUsuario(UsuarioDTO usuarioDTO) throws ValidacaoError {

        MensagemErroResponse mensagemErroResponse = new MensagemErroResponse();


        if (!EmailValidator.getInstance().isValid(usuarioDTO.getEmail())) {
            mensagemErroResponse.getMensagens().add(EMAIL_INVALIDO);
        }
        if (usuarioDTO.getDataNascimento() == null) {
            mensagemErroResponse.getMensagens().add("Data de nascimento não informada");

        }

        if (StringUtils.isBlank(usuarioDTO.getSenha()) || usuarioDTO.getSenha().length() < 6) {
            mensagemErroResponse.getMensagens().add("A senha deve ter no mínimo 6 caracteres");
        }

        if (!mensagemErroResponse.getMensagens().isEmpty()) {
            throw new ValidacaoError(mensagemErroResponse);
        }


    }
}