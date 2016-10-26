package br.edu.ifam.saf.dao;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.edu.ifam.saf.modelo.Usuario;

@Stateless
public class UsuarioDAO {
    @PersistenceContext
    private EntityManager em;
    private GenericDAO<Usuario> dao;

    @PostConstruct
    private void init() {
        dao = new GenericDAO<>(em, Usuario.class);
    }


    public Usuario consultarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.email = :email", Usuario.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Usuario consultarPorToken(String token) {
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.token= :token", Usuario.class);
        query.setParameter("token", token);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }

    public Usuario consultar(Integer id) {
        return dao.consultar(id);
    }

    public List<Usuario> listarTodos() {
        return dao.listarTodos();
    }

    public void remover(Usuario entidade) {
        dao.remover(entidade);
    }

    public Usuario atualizar(Usuario entidade) {
        return dao.atualizar(entidade);
    }

}