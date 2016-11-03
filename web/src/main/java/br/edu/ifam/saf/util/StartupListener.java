package br.edu.ifam.saf.util;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusAluguel;
import br.edu.ifam.saf.modelo.Aluguel;
import br.edu.ifam.saf.modelo.Bairro;
import br.edu.ifam.saf.modelo.Categoria;
import br.edu.ifam.saf.modelo.Cidade;
import br.edu.ifam.saf.modelo.Item;
import br.edu.ifam.saf.modelo.ItemAluguel;
import br.edu.ifam.saf.modelo.Usuario;

public class StartupListener implements ServletContextListener {

    @PersistenceContext
    private EntityManager em;

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        popularLocais();
        popularCategorias();
        popularItens();
        popularItemAluguel();

    }

    private void popularLocais() {
        TypedQuery<Cidade> query = em.createQuery("select c from Cidade c", Cidade.class);

        query.setMaxResults(1);
        List<Cidade> cidades = query.getResultList();

        if (cidades.isEmpty()) {
            UserTransaction transaction = getTransaction();
            try {
                transaction.begin();
                Cidade cidade = new Cidade();
                cidade.setNome("Manaus");
                cidade.setEstado("Amazonas");

                cidade = em.merge(cidade);

                Bairro bairro = new Bairro();
                bairro.setNome("Centro");

                bairro.setCidade(cidade);
                em.merge(bairro);

                transaction.commit();
            } catch (Throwable e) {
                try {
                    transaction.rollback();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    private void popularCategorias() {
        TypedQuery<Categoria> query = em.createQuery("select c from Categoria c", Categoria.class);

        query.setMaxResults(1);
        List<Categoria> categorias = query.getResultList();

        if (categorias.isEmpty()) {
            UserTransaction transaction = getTransaction();
            try {
                transaction.begin();
                Categoria categoria = new Categoria();
                categoria.setId(1);
                categoria.setNome("Jetski");

                em.merge(categoria);

                transaction.commit();
            } catch (Throwable e) {
                try {
                    transaction.rollback();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void popularItens() {
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);

        query.setMaxResults(1);
        List<Item> itens = query.getResultList();

        if (itens.isEmpty()) {
            UserTransaction transaction = getTransaction();

            Random random = new Random();

            String[] tipos = {"Prancha", "Jetski", "Bóia", "Colete"};
            String[] marcas = {"Riachuelo", "Renner", "C&A", "Google"};
            String[] modelos = {"500 cavalos", "350cc"};
            String[] desc2 = {"O melhor", "O mais usado", "O mais alugado", "O mais vendido"};
            String[] desc4 = {"do Brasil", "de Manaus", "do Amazonas"};

            try {
                transaction.begin();

                for (int i = 0; i < 15; i++) {
                    Item item = new Item();

                    String tipo = tipos[Math.abs(random.nextInt() % tipos.length)];
                    String marca = marcas[Math.abs(random.nextInt() % marcas.length)];
                    String modelo = modelos[Math.abs(random.nextInt() % modelos.length)];

                    item.setNome(tipo + " " + marca + " " + modelo);
                    String d1 = desc2[Math.abs(random.nextInt() % desc2.length)];
                    String d2 = desc4[Math.abs(random.nextInt() % desc4.length)];
                    item.setDescricao(d1 + " " + d2);
                    item.setMarca(marca);
                    item.setModelo(modelo);
                    item.setPrecoPorHora(10 + (Math.abs(random.nextDouble() * 100)));
                    item.setCategoria(em.find(Categoria.class, 1));

                    em.merge(item);

                }

                transaction.commit();
            } catch (Throwable e) {
                try {
                    transaction.rollback();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    private void popularItemAluguel() {
        TypedQuery<ItemAluguel> query = em.createQuery("select i from ItemAluguel i", ItemAluguel.class);

        query.setMaxResults(1);
        List<ItemAluguel> itemAluguelLista = query.getResultList();

        if (itemAluguelLista.isEmpty()) {
            UserTransaction transaction = getTransaction();
            try {
                transaction.begin();

                Aluguel aluguel = new Aluguel();
                aluguel.setStatus(StatusAluguel.RESERVA_PENDENTE);
                aluguel.setDataHoraInicio(new Date());
                aluguel.setDataHoraDevolucao(new Date());

                aluguel.setId(1);

                Usuario usuario = new Usuario.Builder()
                        .id(1)
                        .nome("Exemplo")
                        .dataNascimento(new Date())
                        .senha(SegurancaUtil.hashSenha("123456"))
                        .cpf("012.123.123-22")
                        .perfil(Perfil.ADMINISTRADOR)
                        .email("usuario@email.com")

                        .build();

                usuario = em.merge(usuario);

                aluguel.setCliente(usuario);
                aluguel.setFuncionario(usuario);

                aluguel = em.merge(aluguel);

                Item item = em.find(Item.class, 1);
                ItemAluguel itemAluguel = new ItemAluguel();
                itemAluguel.setItem(item);
                itemAluguel.setQuantidade(1);


                itemAluguel.setAluguel(aluguel);
                em.merge(itemAluguel);

                transaction.commit();
            } catch (Throwable e) {
                try {
                    transaction.rollback();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private UserTransaction getTransaction() {
        UserTransaction transaction = null;
        try {
            transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
