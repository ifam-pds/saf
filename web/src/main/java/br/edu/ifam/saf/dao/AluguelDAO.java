package br.edu.ifam.saf.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifam.saf.enums.StatusAluguel;
import br.edu.ifam.saf.modelo.Aluguel;


@Stateless
public class AluguelDAO {

    @PersistenceContext
    private EntityManager em;
    private GenericDAO<Aluguel> dao;

    @PostConstruct
    private void init(){
        dao = new GenericDAO<>(em, Aluguel.class);
    }

    public void inserir(Aluguel entidade) {
        dao.inserir(entidade);
    }

    public Aluguel atualizar(Aluguel entidade) {
        return dao.atualizar(entidade);
    }

    public List<Aluguel> listarTodos() {
        return dao.listarTodos();
    }

    public Aluguel consultar(Integer id) {
        return dao.consultar(id);
    }

    public void remover(Aluguel entidade) {
        dao.remover(entidade);
    }

    public List<Aluguel> filtrarPorStatus(StatusAluguel status) {
        return em.createQuery("SELECT i FROM Aluguel i WHERE i.status = :status", Aluguel.class)
                .setParameter("status", status)
                .getResultList();
    }
}
