import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class APISuitTest {

    String header = "786b9875-e427-433f-bbbd-ceb9338504aa";
    String url = "https://digitalapi.auspost.com.au/postcode/search.json";
    String suburb = "Randwick";
    String state = "NSW";

    @Test
    public void AusPostStatusTest() {
        given().
                header("auth-key", header).and().
                param("q", suburb).and().
                param("state", state).
                when().
                get(url).
                then().
                assertThat().statusCode(200);

    }

    @Test
    public void AupostPostCodeCheck() {
        given().
                header("auth-key", header).and().
                param("q", suburb).and().
                param("state", state).

                when().
                get(url).
                then().
                assertThat().body("localities.locality[0].postcode", equalTo(2031));
    }
    @Test
    public void AuspostCheckCountry() {
        Response res =  given().
                            header("auth-key", header).
                        when().
                            get("https://digitalapi.auspost.com.au/postage/country?code=AF");
                JsonPath j = new JsonPath(res.asString());

                int s =  j.getInt("countries.country.size()");
                for(int i = 0; i < s; i++){
                    String code = j.getString("countries.country["+i+"].code");
                    if(code == "DE"){
                        System.out.println(code);
                    }
                    else {
                        break;
                    }

                }

    }


}
