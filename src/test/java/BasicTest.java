import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class BasicTest {

    public static String authToken;
    public static String itemlists;

    @Test
    public void testStatusCode() {
        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testSigning() {
        given()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
    }

    @Test
    public void printResponse() {
        Response res = given().when()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        //System.out.println(res.asString());

        //System.out.println("-------------------------");
        System.out.println(res.prettyPrint());

    }


    @Test
    public void testCurrency() {
        Response res = given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Map> products = jsonPathEvaluator.getList("data");
        for (Map product : products) {
            Map attributes = (Map) product.get("attributes");
            //System.out.println("Currency List",attributes.get("currency"));
            Assert.assertTrue(attributes.get(("currency")).equals("USD"));

        }
    }

    @Test
    public void testFilter() {
        Response res = given()
                .log().all()
                .queryParam("filter[name]", "bag")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPrint());

    }

    @Test
    public void testFilterp() {
        Response res = given()
                .log().all()
                .queryParam("filter[price]", "19.99")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
    }

    @Test
    public void testFilterid() {
        Response res = given()
                .log().all()
                .queryParam("filter[id]", "3")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
    }

    @Test
    public void testFiltertax() {
        Response res = given()
                .log().all()
                .queryParam("filter[taxons]", "11")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
    }


    @Test
    public void testFiltercolor() {
        Response res = given()
                .log().all()
                .queryParam("filter[color]", "XL", "red")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
    }

    @Test
    public void testFilterpage() {
        Response res = given()
                .log().all()
                .queryParam("filter[page]", "1")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
    }

    @BeforeClass
    public void authToken() {
        Response res = given()
                .formParam("grant_type", "password")
                .formParam("username", "kanigirinikhila9@gmail.com")
                .formParam("password", "New1234")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
        System.out.println(res.prettyPrint());
        authToken = "Bearer " + res.path("access_token");
        System.out.println(authToken);
    }


    @Test
    public void testPostCall1() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);
        String createBody = "{\n" +
                "  \"variant_id\": \"17\",\n" +
                "  \"quantity\": 5\n" +
                "}";
        Response res = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void testPostCall2() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);
        String createBody = "{\n" +
                "  \"variant_id\": \"11\",\n" +
                "  \"quantity\": 5\n" +
                "}";
        Response res = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void viewCart() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);
        Response res = given()
                .headers(headers)
                //.queryParam("filter[cart]=total")
                //.queryParam("filter[cart]=currency")
                //.queryParam("filter[cart]=number")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
        //Assert.assertEquals(res.statusCode(), 200);
        System.out.println(res.jsonPath().prettify());

        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Map> itemlist = (List<Map>) jsonPathEvaluator.getList("data").get(Integer.parseInt("relationships"));
        for (Map itemlist : itemlists) {
            Map itemlists = (Map) itemlist.get("line-items.data");
            //System.out.println("Currency List",attributes.get("currency"));
            Assert.assertEquals(res.statusCode(), 200);

        }
    }

    @Test
    public void deleteItem(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authToken);
        Response res = given()
                .headers(headers)
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/"+itemlists);
        Assert.assertEquals(res.statusCode(), 200);

    }

}






