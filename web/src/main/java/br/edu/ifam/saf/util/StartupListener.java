package br.edu.ifam.saf.util;

import org.apache.commons.collections.Buffer;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.io.BufferedLineReaderInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

                Categoria categoria1 = new Categoria();
                categoria1.setId(2);
                categoria1.setNome("Colete");
                em.merge(categoria1);

                Categoria categoria2 = new Categoria();
                categoria2.setId(3);
                categoria2.setNome("Bóia");
                em.merge(categoria2);

                Categoria categoria3 = new Categoria();
                categoria3.setId(4);
                categoria3.setNome("Prancha");
                em.merge(categoria3);

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

    private void copiarAssetsParaPastaUploads(String nomeArquivo) {

        try {
            IOUtils.copy(getClass().getClassLoader().getResourceAsStream("img/"+nomeArquivo),
                    new FileOutputStream(ArquivoEndpoint.UPLOAD_DIR + File.separator + nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void popularItens() {
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);

        query.setMaxResults(1);
        List<Item> itens = query.getResultList();

        if (itens.isEmpty()) {


            UserTransaction transaction = getTransaction();
            String line;
            String cvsSplit = ",";

            try {
                transaction.begin();
                InputStream is = getClass().getClassLoader().getResourceAsStream("archives/cadastroItens.csv");
                InputStreamReader file = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(file);

                while ((line = br.readLine()) != null) {
                    String[] jetski = line.split(cvsSplit);

                    Item item = new Item();
                    item.setStatus(StatusItem.ATIVO);
                    item.setCategoria(em.find(Categoria.class, Integer.parseInt(jetski[0].replace("\"",""))));
                    item.setMarca(jetski[1].replace("\"",""));
                    item.setModelo(jetski[2].replace("\"",""));
                    item.setNome(jetski[3].replace("\"",""));
                    item.setDescricao(jetski[4].replace("\"",""));
                    item.setPrecoPorHora(Double.parseDouble(jetski[5].replace("\"","")));
                    item.setImagem(jetski[6].replace("\"",""));
                    copiarAssetsParaPastaUploads(jetski[6].replace("\"",""));

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
