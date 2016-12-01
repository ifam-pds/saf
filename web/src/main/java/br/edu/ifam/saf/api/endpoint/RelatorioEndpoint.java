package br.edu.ifam.saf.api.endpoint;


import org.codehaus.jackson.map.ObjectMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.ItemRelatorio;
import br.edu.ifam.saf.api.data.ItemRelatorioResponse;
import br.edu.ifam.saf.api.util.MediaType;

@SuppressWarnings("unchecked")
@Stateless
@Path("/relatorios")
public class RelatorioEndpoint {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/mais_alugados")
    public Response maisAlugados() {
        Query query = em.createNativeQuery(
                "SELECT\n" +
                        "  i.nome       AS descricao,\n" +
                        "  count(ia.id) AS total\n" +
                        "\n" +
                        "FROM item i\n" +
                        "  JOIN item_aluguel ia ON ia.item_id = i.id\n" +
                        "GROUP BY i.id");

        List<ItemRelatorio> itens = new ArrayList<>();

        List<Object[]> results = (List<Object[]>) query.getResultList();
        for (Object[] result : results) {
            itens.add(new ItemRelatorio(((String) result[0]), ((Number) result[1]).doubleValue()));
        }


        return Response.ok(new ItemRelatorioResponse(itens)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/usuario_mais_frequentes")
    public Response usuarioMaisFrequentes() {
        Query query = em.createNativeQuery(
                "SELECT\n" +
                        "  u.nome,\n" +
                        "  count(ia.aluguel_id)\n" +
                        "FROM item_aluguel ia\n" +
                        "  JOIN aluguel a ON ia.aluguel_id = a.id\n" +
                        "  JOIN usuario u ON a.cliente_id = u.id\n" +
                        "GROUP BY u.id");

        List<ItemRelatorio> itens = new ArrayList<>();

        List<Object[]> results = (List<Object[]>) query.getResultList();
        for (Object[] result : results) {
            itens.add(new ItemRelatorio(((String) result[0]), ((Number) result[1]).doubleValue()));
        }


        return Response.ok(new ItemRelatorioResponse(itens)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/media_itens_por_aluguel")
    public Response mediaItensPorAluguel() {
        Query query = em.createNativeQuery(
                "SELECT AVG(c)\n" +
                        "FROM (SELECT count(ia.item_id) AS c\n" +
                        "      FROM item_aluguel ia\n" +
                        "      GROUP BY ia.aluguel_id) c");

        Number media = (Number) query.getSingleResult();


        return Response.ok(new ItemRelatorioResponse(new ItemRelatorio("Média", media.doubleValue()))).build();
    }
}
