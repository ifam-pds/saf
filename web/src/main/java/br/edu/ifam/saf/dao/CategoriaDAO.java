package br.edu.ifam.saf.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifam.saf.modelo.Categoria;

@Stateless
public class CategoriaDAO {

    @PersistenceContext
    EntityManager em;

    private GenericDAO<Categoria> dao;

    @PostConstruct
    public void init() {
        dao = new GenericDAO<>(em, Categoria.class);
    }

    public Categoria consultar(Integer id) {
        return dao.consultar(id);
    }

    public List<Categoria> listarTodos() {
        return dao.listarTodos();
    }

    public void remover(Categoria entidade) {
        dao.remover(entidade);
    }

    public Categoria atualizar(Categoria entidade) {
        return dao.atualizar(entidade);
    }

}
