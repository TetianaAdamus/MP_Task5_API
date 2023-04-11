package stepDefs;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;

import dto.CartCreated;
import dto.CartObject;
import dto.Product;
import dto.SessionStorage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;


public class ApiSteps {

    private String BASE_URL = "https://www.kruidvat.nl";
    private String CREATE_CART_URL = "/api/v2/kvn/users/anonymous/carts";

    private SessionStorage sessionStorage = new SessionStorage();

    @Given("User created a cart via api")
    public void userCreatedACartViaApi() {
        Response response = given()
                .when()
                .post(BASE_URL + CREATE_CART_URL)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response();

        CartCreated cartCreatedObject = response.as(CartCreated.class);
        sessionStorage.storeObject("guid", cartCreatedObject.getGuid());

//        Specification.requestSpecific(BASE_URL);
//        CartCreated cartCreatedObject = given()
//                .when()
//                .post(CREATE_CART_URL)
//                .then()
//                .statusCode(SC_CREATED)
//                .log().all()
//                .extract()
//                .as(CartCreated.class);
//
//        sessionStorage.storeObject("guid", cartCreatedObject.getGuid());
    }

    @And("User add the product {string} to cart and {int} quantity via api")
    public void userAddTheProductToCartAndQuantityViaApi(String productCode, int quantity) {
        Specification.requestSpecific(BASE_URL);
        given()
                .body(addProductToCartPayload(productCode, quantity))
                .when()
                .post("/api/v2/kvn/users/anonymous/carts/{guid}/entries", sessionStorage.getStoreObject("guid"));
    }

    private CartObject addProductToCartPayload(String productCode, int qty) {
        CartObject cartDTO = new CartObject();
        Product productDTO = new Product();
        productDTO.setCode(productCode);
        cartDTO.setProduct(productDTO);
        cartDTO.setQuantity(qty);
        return cartDTO;
    }

}
