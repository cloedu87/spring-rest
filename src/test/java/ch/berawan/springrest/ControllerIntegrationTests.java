package ch.berawan.springrest;

import ch.berawan.springrest.data.repository.StockLevelRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    //    //@MockBean
    @Resource
    private StockLevelRepository stockLevelRepository;

    private void initializeTest() {

        stockLevelRepository.deleteAll();
        stockLevelRepository.saveAll(CONSTANTS.initialStocklevels());
    }

    @Test
    public void testGetAllStockLevels() throws Exception {

        //arrange
        initializeTest(); //initialize the db with 9 stock levels

        //act
        this.mockMvc.perform(
                get("/stocklevel/all")

                //assert
        ).andExpect(status().isOk());

        Assert.assertTrue(stockLevelRepository.findAll().size() == 9); //assure there will be 9 returned from the db

    }

    @Test
    public void testDataInit() throws Exception {

        //arrange
        stockLevelRepository.deleteAll();//we wanna have empty db to test plain insert here

        //act
        this.mockMvc.perform(
                post("/stocklevel/init")
                        .content(CONSTANTS.TEST_STOCK_LEVELS_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                    //assert
                    .andExpect(status().isIAmATeapot());

        Assert.assertTrue(stockLevelRepository.findAll().size() == 9); //assure there will be 9 returned from the db
    }

    @Test
    public void testGetSpecificStockLevelById_positive() throws Exception {

        //arrange
        initializeTest();

        //act
        this.mockMvc.perform(
                get("/stocklevel/1001")
        )
                    //assert
                    .andExpect(status().isOk());
    }

    @Test
    public void testGetSpecificStockLevelById_negative_badID() throws Exception {

        //arrange
        initializeTest();
        final String BAD_ID = "1001eee";

        //act
        this.mockMvc.perform(
                get("/stocklevel/" + BAD_ID)
        )

                    //assert
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetSpecificStockLevelById_negative_wrongId() throws Exception {

        //arrange
        initializeTest();
        final String WRONG_ID = "42";

        //act
        this.mockMvc.perform(
                get("/stocklevel/" + WRONG_ID)
        )

                    //assert
                    .andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void testUpdateSpecificStockLevel_positive() throws Exception {

        //arrange
        initializeTest();

        //act
        this.mockMvc.perform(
                put("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
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
    public void testUpdateSpecificStockLevel_negative() throws Exception {

        //arrange
        initializeTest();

        //act
        this.mockMvc.perform(
                put("/stocklevel/NON-EXISTING-PRODUCT/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                    //assert
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddStockLevel_positive() throws Exception {

        //arrange
        stockLevelRepository.deleteAll();//we wanna have empty db to test plain insert here

        //act
        this.mockMvc.perform(
                post("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                    //assert
                    .andExpect(status().isCreated());
    }

    @Test
    public void testModifyStockLevel_positive() throws Exception {

        //arrange
        initializeTest(); //after initialization Stocklevel.level should be 33.

        //act
        this.mockMvc.perform(
                patch("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_MODIFY_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
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
    public void testModifyStockLevel_negative() throws Exception {

        //arrange
        initializeTest();

        //act
        this.mockMvc.perform(
                patch("/stocklevel/NON-EXISTING-PRODUCT/warehouse1")
                        .content(CONSTANTS.TEST_MODIFY_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                    //assert
                    .andExpect(status().isBadRequest());
    }
}