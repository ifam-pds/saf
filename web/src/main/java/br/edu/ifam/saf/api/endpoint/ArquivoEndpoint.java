package br.edu.ifam.saf.api.endpoint;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.ArquivoResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.util.Respostas;

@Stateless
@Path("/imagens")
public class ArquivoEndpoint {

    public static final String UPLOAD_DIR = System.getProperty("jboss.home.dir") + File.separator + "saf-uploads";


    static {
        File file = new File(UPLOAD_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @GET
    @Path("")
    public String file() {
        return "<html>\n" +
                "<body>\n" +
                "<form action=\"\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "    <p>Arquivo: <input type=\"file\" name=\"arquivo\" size=\"50\" /></p>\n" +
                "    <p>Nome: <input type=\"text\" name=\"nomeArquivo\" size=\"50\" /></p>\n" +
                "    <input type=\"submit\" value=\"Upload\" />\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>\n";

    }

    @POST
    @Path("")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(br.edu.ifam.saf.api.util.MediaType.APPLICATION_JSON_UTF8)
    public Response upload(@MultipartForm ArquivoEntity entity) throws UnsupportedEncodingException {


        if (entity.getArquivo() == null) {
            return Respostas.badRequest("Arquivo não enviado");
        }

        String safeName = URLEncoder.encode(entity.getNomeArquivo(), "UTF-8");

        String extensao = FilenameUtils.getExtension(safeName);
        String nomeBase = FilenameUtils.getBaseName(safeName);


        String nomeArquivo = nomeBase + "-" + DigestUtils.sha256Hex(entity.getArquivo()) + "." + extensao;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(UPLOAD_DIR + File.separator + nomeArquivo);
            byte[] filebytes = entity.getArquivo();
            fos.write(filebytes);
        } catch (IOException e) {
            return Respostas.erroInterno(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return Respostas.ok(new ArquivoResponse(nomeArquivo));
    }

    @GET
    @Path("{file_id}")
    public Response serve(@PathParam("file_id") String fileId) throws IOException {

        File file = new File(UPLOAD_DIR + File.separator + fileId);
        if (!file.getCanonicalPath().startsWith(UPLOAD_DIR) || !file.exists()) {
            return Response.status(400).type(MediaType.APPLICATION_JSON_TYPE).entity(new MensagemErroResponse("Arquivo desconhecido")).build();
        }

        FileInputStream fis = new FileInputStream(file);
        InputStream is = new BufferedInputStream(fis);
        String mimeType = URLConnection.guessContentTypeFromStream(is);

        fis.close();
        is.close();

        return Response.status(200).type(mimeType).entity(file).build();

    }


    public static class ArquivoEntity {

        @FormParam("nomeArquivo")
        private String nomeArquivo;

        @FormParam("arquivo")
        private byte[] arquivo;

        public byte[] getArquivo() {
            return arquivo;
        }

        public void setArquivo(byte[] arquivo) {
            this.arquivo = arquivo;
        }

        public String getNomeArquivo() {
            return nomeArquivo;
        }

        public void setNomeArquivo(String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
        }
    }
}
