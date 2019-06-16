package ch.berawan.springrest;

import ch.berawan.springrest.data.repository.StockLevelRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerIntegrationTests {

    final Logger logger = LoggerFactory.getLogger(ControllerIntegrationTests.class);

    private String accessToken;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    //    //@MockBean
    @Resource
    private StockLevelRepository stockLevelRepository;

    //    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    private String obtainAccessToken() throws Exception {

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
//        params.add("client_id", "spring-rest-client");
        params.add("username", "awan");
        params.add("password", "berawan");

        final ResultActions result
                = mockMvc.perform(post("/oauth/token")
                                          .params(params)
                                          .with(httpBasic("spring-rest-client", "spring-rest-secret"))
                                          .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                         .andExpect(
                                 content().contentType(MediaType.APPLICATION_JSON_UTF8))
                         .andExpect(
                                 status().isOk())
                         .andExpect(
                                 jsonPath("$.access_token").exists());

        final String resultString = result
                .andReturn()
                .getResponse()
                .getContentAsString();

        final JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    private void initializeTestData() {
        stockLevelRepository.deleteAll();
        stockLevelRepository.saveAll(CONSTANTS.initialStocklevels());
    }

    @Before
    public void setup() throws Exception {
        this.accessToken = obtainAccessToken();
    }

    @Test
    public void resourceServerAuthentication() throws Exception {
        //arrange

        //act
        this.mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "password")
                        .param("username", "awan")
                        .param("password", "berawan")
                        .with(httpBasic("spring-rest-client", "spring-rest-secret")))

                    //assert
                    .andExpect(
                            content()
                                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(
                            status().isOk())
                    .andExpect(jsonPath("$.access_token").exists());
    }

    @Test
    public void getAllStockLevels() throws Exception {
        //arrange
        initializeTestData(); //initialize the db with 9 stock levels

        //act
        this.mockMvc.perform(
                get("/stocklevel/all")
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isOk());

        Assert.assertTrue(stockLevelRepository.findAll().size() == 9); //assure there will be 9 returned from the db

    }

    @Test
    public void dataInit() throws Exception {

        //arrange
        stockLevelRepository.deleteAll();//we wanna have empty db to test plain insert here

        //act
        this.mockMvc.perform(
                post("/stocklevel/init")
                        .content(CONSTANTS.TEST_STOCK_LEVELS_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )

                    //assert
                    .andExpect(status().isIAmATeapot());

        Assert.assertTrue(stockLevelRepository.findAll().size() == 9); //assure there will be 9 returned from the db
    }

    @Test
    public void getSpecificStockLevelById_positive() throws Exception {

        //arrange
        initializeTestData();

        //act
        this.mockMvc.perform(
                get("/stocklevel/1001")
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isOk());
    }

    @Test
    public void getSpecificStockLevelById_negative_badID() throws Exception {

        //arrange
        initializeTestData();
        final String BAD_ID = "1001eee";

        //act
        this.mockMvc.perform(
                get("/stocklevel/" + BAD_ID)
                        .header("Authorization", "Bearer " + this.accessToken)
        )

                    //assert
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void getSpecificStockLevelById_negative_wrongId() throws Exception {

        //arrange
        initializeTestData();
        final String WRONG_ID = "42";

        //act
        this.mockMvc.perform(
                get("/stocklevel/" + WRONG_ID)
                        .header("Authorization", "Bearer " + this.accessToken)
        )

                    //assert
                    .andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void updateSpecificStockLevel_positive() throws Exception {

        //arrange
        initializeTestData();

        //act
        this.mockMvc.perform(
                put("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isOk());

        //after update, level should be 42. ensure overwrite of Stocklevel.level in database from level=33 to level=42 was successful
        Assert.assertTrue(stockLevelRepository.findFirstByProductAndWarehouse(

                CONSTANTS.TEST_STOCK_LEVEL.getProduct(),
                CONSTANTS.TEST_STOCK_LEVEL.getWarehouse()

        ).getLevel() == 42);
    }

    @Test
    public void updateSpecificStockLevel_negative() throws Exception {

        //arrange
        initializeTestData();

        //act
        this.mockMvc.perform(
                put("/stocklevel/NON-EXISTING-PRODUCT/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void addStockLevel_positive() throws Exception {

        //arrange
        stockLevelRepository.deleteAll();//we wanna have empty db to test plain insert here

        //act
        this.mockMvc.perform(
                post("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isCreated());
    }

    @Test
    public void modifyStockLevel_positive() throws Exception {

        //arrange
        initializeTestData(); //after initialization Stocklevel.level should be 33.

        //act
        this.mockMvc.perform(
                patch("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_MODIFY_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isOk());

        //after modification, level should be 34. ensure increment by 1 in database from level=33 to level=34 was successful
        Assert.assertTrue(stockLevelRepository.findFirstByProductAndWarehouse(

                CONSTANTS.TEST_STOCK_LEVEL.getProduct(),
                CONSTANTS.TEST_STOCK_LEVEL.getWarehouse()

        ).getLevel() == 34);
    }

    @Test
    public void modifyStockLevel_negative() throws Exception {

        //arrange
        initializeTestData();

        //act
        this.mockMvc.perform(
                patch("/stocklevel/NON-EXISTING-PRODUCT/warehouse1")
                        .content(CONSTANTS.TEST_MODIFY_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + this.accessToken)
        )
                    //assert
                    .andExpect(status().isBadRequest());
    }
}