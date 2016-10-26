package br.edu.ifam.saf.dao;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifam.saf.modelo.Bairro;

@Stateless
public class BairroDAO {
    @PersistenceContext
    private EntityManager em;
    private GenericDAO<Bairro> dao;

    @PostConstruct
    private void init() {
        dao = new GenericDAO<>(em, Bairro.class);
    }

    public void inserir(Bairro entidade) {
        dao.inserir(entidade);
    }

    public Bairro consultar(Integer id) {
        return dao.consultar(id);
    }

    public List<Bairro> listarTodos() {
        return dao.listarTodos();
    }

    public void remover(Bairro entidade) {
        dao.remover(entidade);
    }

    public Bairro atualizar(Bairro entidade) {
        return dao.atualizar(entidade);
    }
}