package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do módulo de Produtos")
public class ProdutoTest {

    private String token;

    @BeforeEach
    public void berforeEach () {
        // Configurando os dados da API Rest da lojinha
        baseURI = "http://165.227.93.41";
        // port = 8080; necessário quando houver porta configurada
        basePath = "/lojinha";

        UsuarioDataFactory.criarUsuarioAdmin();

        // Obter o token do usuário ADMIN
        this.token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdmin())
            .when()
                .post("/v2/login")
            .then()
                .extract()
                .path("data.token");
    }

    @Test
    @DisplayName("Validar que o produto é cadastrado com sucesso")
    public void testValidarCadstramentoProdutoComSucesso () {

        // Tentar inserir um produto com o valor de 7000.01 e validar que a mensagem de erro foi validada
        // e o erro do status code foi 422
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(6999.00))
                .when()
                .post("/v2/produtos")
                .then()
                .assertThat()
                .body("message", equalTo("Produto adicionado com sucesso"))
                .statusCode(201);
    }

    @Test
    @DisplayName("Validar que o valor do produto igual a 0.00 não é permitido")
    public void testValidarLimiteZeradoProibidoValorProduto () {

        // Tentar inserir um produto com o valor de 0.00 e validar que a mensagem de erro foi validada
        // e o erro do status code foi 422
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }

    @Test
    @DisplayName("Validar que o valor do produto igual a 7000.01 não é permitido")
    public void testValidarLimiteMaiorSeteMilProibidoValorProduto () {

        // Tentar inserir um produto com o valor de 7000.01 e validar que a mensagem de erro foi validada
        // e o erro do status code foi 422
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
}
