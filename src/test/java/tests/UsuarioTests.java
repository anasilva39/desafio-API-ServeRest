package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTests {
    private static String usuarioId;
    private static final String BASE_URL = "https://serverest.dev";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    @DisplayName("Criar um novo usuário")
    public void testCriarUsuario() {
        Response getResponse = given()
                .queryParam("email", "teste@example.com")
                .when()
                .get("/usuarios");

        // Se o usuário já existir, deletamos antes de criar um novo
        if (getResponse.statusCode() == 200 && !getResponse.jsonPath().getList("usuarios").isEmpty()) {
            String existingUserId = getResponse.jsonPath().getString("usuarios[0]._id");
            given()
                    .when()
                    .delete("/usuarios/" + existingUserId)
                    .then()
                    .statusCode(200)
                    .log().all();
        }

        String jsonBody = "{" +
                "\"nome\":\"Teste Usuario\"," +
                "\"email\":\"teste@example.com\"," +
                "\"password\":\"123456\"," +
                "\"administrador\":\"true\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/usuarios");

        response.then()
                .log().all()
                .statusCode(201)
                .body("message", equalTo("Cadastro realizado com sucesso"));

        usuarioId = response.jsonPath().getString("_id");
    }

    @Test
    @Order(2)
    @DisplayName("Listar todos os usuários")
    public void testListarUsuarios() {
        given()
                .when()
                .get("/usuarios")
                .then()
                .log().all()
                .statusCode(200)
                .body("usuarios", not(empty()));
    }

    @Test
    @Order(3)
    @DisplayName("Buscar usuário por ID")
    public void testBuscarUsuarioPorId() {
        given()
                .when()
                .get("/usuarios/" + usuarioId)
                .then()
                .log().all()
                .statusCode(200)
                .body("_id", equalTo(usuarioId));
    }

    @Test
    @Order(4)
    @DisplayName("Atualizar usuário")
    public void testAtualizarUsuario() {
        String emailUnico = "teste" + System.currentTimeMillis() + "@example.com";

        String jsonBody = "{" +
                "\"nome\":\"Teste Usuario Atualizado\"," +
                "\"email\":\"" + emailUnico + "\"," +
                "\"password\":\"654321\"," +
                "\"administrador\":\"true\"}";

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put("/usuarios/" + usuarioId)
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Registro alterado com sucesso"));
    }

    @Test
    @Order(5)
    @DisplayName("Deletar usuário")
    public void testDeletarUsuario() {
        given()
                .when()
                .delete("/usuarios/" + usuarioId)
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Registro excluído com sucesso"));
    }

    @Test
    @Order(6)
    @DisplayName("Tentar criar um usuário com e-mail já cadastrado")
    public void testCriarUsuarioEmailJaCadastrado() {
        String jsonBody = "{" +
                "\"nome\":\"Teste Usuario\"," +
                "\"email\":\"teste@example.com\"," +
                "\"password\":\"123456\"," +
                "\"administrador\":\"true\"}";

        // Cria o primeiro usuário
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(201)  // Espera-se o status 201 para criação do usuário
                .body("message", equalTo("Cadastro realizado com sucesso"));

        // Tenta criar o mesmo usuário novamente e valida o erro esperado
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/usuarios")
                .then()
                .log().all()
                .statusCode(400)  // Espera-se o status 400 para e-mail já cadastrado
                .body("message", equalTo("Este email já está sendo usado")); // Valida a mensagem de erro
    }


}
