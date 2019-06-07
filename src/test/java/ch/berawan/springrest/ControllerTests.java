package ch.berawan.springrest;

import ch.berawan.springrest.controller.StockLevelController;
import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.data.repository.StockLevelRepository;
import ch.berawan.springrest.service.StockLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StockLevelController.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockLevelRepository stockLevelRepository;

    @Test
    public void testDataInit() throws Exception {

        //arrange
        given(stockLevelRepository.saveAll(any()))
                .willReturn(CONSTANTS.initialStocklevels());

        //act
        this.mockMvc.perform(
                post("/stocklevel/init")
                        .content(CONSTANTS.TEST_STOCK_LEVELS_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                //assert
                .andExpect(status().isIAmATeapot());
    }

    @Test
    public void testGetSpecificStockLevelById_positive() throws Exception {

        //arrange
        final StockLevel sampleStockLevel = new StockLevel(1001l, "product1", "warehouse1", 42);

        given(stockLevelRepository.findFirstById(1001l))
                .willReturn(sampleStockLevel);

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
        final String WRONG_ID = "42";
        given(stockLevelRepository.findFirstById(1001l))
                .willReturn(null);
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
        given(stockLevelRepository.updateStockLevel(any()))
                .willReturn(1l);

        //act
        this.mockMvc.perform(
                put("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                //assert
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateSpecificStockLevel_negative() throws Exception {

        //arrange
        given(stockLevelRepository.updateStockLevel(any()))
                .willReturn(0l);

        //act
        this.mockMvc.perform(
                put("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                //assert
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddStockLevel_positive() throws Exception {

        //arrange
        given(stockLevelRepository.save(any()))
                .willReturn(CONSTANTS.STOCK_LEVEL);

        //act
        this.mockMvc.perform(
                post("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                //assert
                .andExpect(status().isCreated()).andExpect(content().string(CONSTANTS.TEST_STOCK_LEVEL_JSON));
    }

    @Test
    public void testAddStockLevel_negative() throws Exception {

        //arrange
        given(stockLevelRepository.save(any()))
                .willReturn(null);

        //act
        this.mockMvc.perform(
                post("/stocklevel/product1/warehouse1")
                        .content(CONSTANTS.TEST_STOCK_LEVEL_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                //assert
                .andExpect(status().isBadRequest());
    }
}