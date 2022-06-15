package dataFactory;

import pojo.CompontentePojo;
import pojo.ProdutoPojo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {
    public static ProdutoPojo criarProdutoComumComOValorIgualA(double valor) {
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Smart TV Sansumg");
        produto.setProdutoValor(valor);

        List<String> cores = new ArrayList<>();
        cores.add("cinza");
        cores.add("cinza");
        produto.setProdutoCores(cores);

        produto.setProdutoUrlMock("");

        List<CompontentePojo> compontentes = new ArrayList<>();

        CompontentePojo compontente = new CompontentePojo();
        compontente.setComponenteNome("Cabo de energia");
        compontente.setComponenteQuantidade(1);
        compontentes.add(compontente);

        CompontentePojo segundoComponente = new CompontentePojo();
        segundoComponente.setComponenteNome("Controle");
        segundoComponente.setComponenteQuantidade(1);
        compontentes.add(segundoComponente);

        produto.setCompontente(compontentes);

        return produto;
    }
}
