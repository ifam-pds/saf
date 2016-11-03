package br.edu.ifam.saf.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifam.saf.modelo.ItemAluguel;

@Stateless
public class ItemAluguelDAO {

    @PersistenceContext
    private EntityManager em;
    private GenericDAO<ItemAluguel> dao;

    @PostConstruct
    private void init(){
        dao = new GenericDAO<>(em, ItemAluguel.class);
    }

    public void inserir(ItemAluguel entidade) {
        dao.inserir(entidade);
    }

    public ItemAluguel atualizar(ItemAluguel entidade) {
        return dao.atualizar(entidade);
    }

    public List<ItemAluguel> listarTodos() {
        return dao.listarTodos();
    }

    public ItemAluguel consultar(Integer id) {
        return dao.consultar(id);
    }

    public void remover(ItemAluguel entidade) {
        dao.remover(entidade);
    }

    public List<ItemAluguel> listarTodosAlugados() {
        return em.createQuery("select o from ItemAluguel o where o.status='APROVADO'", ItemAluguel.class).getResultList();
    }
}
