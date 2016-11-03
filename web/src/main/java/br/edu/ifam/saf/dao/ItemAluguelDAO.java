package br.edu.ifam.saf.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifam.saf.modelo.Item_Aluguel;

@Stateless
public class ItemAluguelDAO {

    @PersistenceContext
    private EntityManager em;
    private GenericDAO<Item_Aluguel> dao;

    @PostConstruct
    private void init(){
        dao = new GenericDAO<>(em, Item_Aluguel.class);
    }

    public void inserir(Item_Aluguel entidade) {
        dao.inserir(entidade);
    }

    public Item_Aluguel atualizar(Item_Aluguel entidade) {
        return dao.atualizar(entidade);
    }

    public List<Item_Aluguel> listarTodos() {
        return dao.listarTodos();
    }

    public Item_Aluguel consultar(Integer id) {
        return dao.consultar(id);
    }

    public void remover(Item_Aluguel entidade) {
        dao.remover(entidade);
    }

}
