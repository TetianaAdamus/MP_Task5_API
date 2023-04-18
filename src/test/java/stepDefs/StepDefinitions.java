package stepDefs;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.driver;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Constants.BASE_URL;
import static utils.Constants.CREATE_CART_URL;
import static utils.Constants.GUID;
import static utils.WaiterUtils.waitForPageLoadComplete;

import dto.CartCreateResponse;
import dto.CartRequest;
import dto.ProductRequest;
import dto.SessionStorage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Cookie;

import java.io.InputStream;


public class StepDefinitions {

    protected SessionStorage sessionStorage = new SessionStorage();


    @Given("User created a cart via api")
    public void userCreatedACartViaApi() {
        Response response = given()
                .spec(Specification.requestSpecific(BASE_URL))
                .when()
                .post(CREATE_CART_URL)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response();

        CartCreateResponse cartCreatedObject = response.as(CartCreateResponse.class);
        sessionStorage.storeObject(GUID, cartCreatedObject.getGuid());
    }

    @And("User add the product {string} to cart with {int} quantity via api")
    public void userAddTheProductToCartAndQuantityViaApi(String productCode, int quantity) {
        InputStream createBookingJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("addproductjsonsheme.json");

        Response response = given()
                .spec(Specification.requestSpecific(BASE_URL))
                .body(addProductToCartPayload(productCode, quantity))
                .when()
                .post("/api/v2/kvn/users/anonymous/carts/{guid}/entries", sessionStorage.getStoreObject(GUID, String.class))
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBookingJsonSchema))
                .and()
                .log().all()
                .extract().response();

        String getProductCodeFromJson = response.jsonPath().getString("entry.product.code");
        int getProductQuantityFromJson = response.jsonPath().getInt("quantity");

        sessionStorage.storeObject("productCode", getProductCodeFromJson);
        sessionStorage.storeObject("productQuantity", getProductQuantityFromJson);

// Attempt to take a code value from POJO class:
//        AddProductResponse addProductResponse = response.as(AddProductResponse.class);
//        sessionStorage.storeObject("productCode", addProductResponse.getProduct().getCode());
    }

    @Then("{int} product {string} is added to cart via api")
    public void cartContainsProduct(int quantity, String product) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(sessionStorage.getStoreObject("productQuantity", Integer.class)).isEqualTo(quantity);
        softly.assertThat(sessionStorage.getStoreObject("productCode", String.class)).isEqualTo(product);
    }

    @When("User opens Home Page")
    public void openHomePage() {
        open(BASE_URL);
        waitForPageLoadComplete();
    }

    @And("Authenticate to web application by adding {string} cookie")
    public void authenticateViaCookie(String cookie) {
        waitForPageLoadComplete();
        driver().getWebDriver().manage().deleteAllCookies();
        driver().getWebDriver().manage().addCookie(new Cookie(cookie, sessionStorage.getStoreObject(GUID, String.class)));
        waitForPageLoadComplete();
        driver().getWebDriver().navigate().refresh();
        waitForPageLoadComplete();
    }

    @And("User opens Basket Page")
    public void openBasketPage() {
        open(BASE_URL+"/cart");
        waitForPageLoadComplete();
        closeCookiePopup();
    }

    @Then("Basket contains {int} expected product")
    public void basketContainsExpectedProduct(int productQuantity) {
        assertThat(getProductsNumberIntoBasket()).isEqualTo(productQuantity);
        $(".product-summary__img-link").click();
        waitForPageLoadComplete();
        assertThat(driver().getWebDriver().getCurrentUrl().contains(sessionStorage.getStoreObject("productCode",
                String.class))).isTrue();
    }

    private CartRequest addProductToCartPayload(String productCode, int qty) {
        ProductRequest productRequest = ProductRequest.builder()
                .code(productCode)
                .build();
        return CartRequest
                .builder()
                .product(productRequest)
                .quantity(qty)
                .build();
    }

    private int getProductsNumberIntoBasket(){
        return $$(".product-summary__row").size();
    }

    private void closeCookiePopup(){
        $("button[id='onetrust-accept-btn-handler'").click();
        waitForPageLoadComplete();
    }

}
