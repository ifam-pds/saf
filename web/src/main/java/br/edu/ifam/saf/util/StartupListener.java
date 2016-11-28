package br.edu.ifam.saf.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import br.edu.ifam.saf.api.endpoint.ArquivoEndpoint;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusItem;
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
        criarUsuarios();
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

    private void copiarAssetsParaPastaUploads() {

        try {
            IOUtils.copy(getClass().getClassLoader().getResourceAsStream("img/lancha01.jpg"), new FileOutputStream(ArquivoEndpoint.UPLOAD_DIR + File.separator + "lancha01.jpg"));
//            IOUtils.copy(getClass().getResourceAsStream("img/img2.png"), new FileOutputStream(ArquivoEndpoint.UPLOAD_DIR + File.separator + "img2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void popularItens() {
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);

        query.setMaxResults(1);
        List<Item> itens = query.getResultList();

        if (itens.isEmpty()) {
            copiarAssetsParaPastaUploads();


            UserTransaction transaction = getTransaction();

            Random random = new Random();

            String[] tipos = {"Prancha", "Jetski", "Bóia", "Colete"};
            String[] marcas = {"Riachuelo", "Renner", "C&A", "Google"};
            String[] modelos = {"500 cavalos", "350cc"};
            String[] desc2 = {"O melhor", "O mais usado", "O mais alugado", "O mais vendido"};
            String[] desc4 = {"do Brasil", "de Manaus", "do Amazonas"};

            try {
                transaction.begin();

                Item item = new Item();
                item.setImagem("lancha01.jpg");

                String tipo = tipos[Math.abs(random.nextInt() % tipos.length)];
                String marca = marcas[Math.abs(random.nextInt() % marcas.length)];
                String modelo = modelos[Math.abs(random.nextInt() % modelos.length)];

                item.setNome(tipo + " " + marca + " " + modelo);
                String d1 = desc2[Math.abs(random.nextInt() % desc2.length)];
                String d2 = desc4[Math.abs(random.nextInt() % desc4.length)];
                item.setDescricao(d1 + " " + d2);
                item.setMarca(marca);
                item.setModelo(modelo);
                item.setPrecoPorHora(10 + (random.nextDouble() * 100));
                item.setCategoria(em.find(Categoria.class, 1));
                item.setStatus(StatusItem.ATIVO);

                em.merge(item);


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
        System.out.println("LISTA: " + itemAluguelLista.size());

        if (itemAluguelLista.isEmpty()) {
            UserTransaction transaction = getTransaction();
            try {
                transaction.begin();

                Aluguel aluguel = new Aluguel();
                aluguel.setDataHoraRequisicao(new Date());

                Usuario usuario = new Usuario.Builder()
                        .id(3)
                        .nome("Não Sou Robô")
                        .dataNascimento(new Date())
                        .senha(SegurancaUtil.hashSenha(""))
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
                itemAluguel.setDuracaoEmMinutos(60);

                ItemAluguel itemAluguel2 = new ItemAluguel();
                itemAluguel2.setItem(item);
                itemAluguel2.setDuracaoEmMinutos(180);


                itemAluguel.setAluguel(aluguel);
                itemAluguel2.setAluguel(aluguel);
                em.merge(itemAluguel);
                em.merge(itemAluguel2);

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

    private void criarUsuarios() {
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u", Usuario.class);

        query.setMaxResults(1);

        List<Usuario> usuarios = query.getResultList();

        if (usuarios.isEmpty()) {
            UserTransaction transaction = getTransaction();

            try {
                transaction.begin();

                Usuario admin = new Usuario.Builder()
                        .id(1)
                        .nome("admin")
                        .dataNascimento(new Date())
                        .senha(SegurancaUtil.hashSenha("123456"))
                        .cpf("123.456.789-52")
                        .perfil(Perfil.ADMINISTRADOR)
                        .email("admin@saf.com")

                        .build();

                em.merge(admin);

                Usuario funcionario = new Usuario.Builder()
                        .id(1)
                        .nome("funcionario")
                        .dataNascimento(new Date())
                        .senha(SegurancaUtil.hashSenha("123456"))
                        .cpf("012.345.678-90")
                        .perfil(Perfil.FUNCIONARIO)
                        .email("func@saf.com")

                        .build();

                em.merge(funcionario);

                Usuario cliente = new Usuario.Builder()
                        .id(1)
                        .nome("cliente")
                        .dataNascimento(new Date())
                        .senha(SegurancaUtil.hashSenha("123456"))
                        .cpf("987.654.321-01")
                        .perfil(Perfil.CLIENTE)
                        .email("cliente@saf.com")

                        .build();

                em.merge(cliente);

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
