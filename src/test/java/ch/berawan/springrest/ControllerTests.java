package ch.berawan.springrest;

import ch.berawan.springrest.controller.StockLevelController;
import ch.berawan.springrest.data.repository.StockLevelRepository;
import ch.berawan.springrest.service.StockLevelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@WebMvcTest(StockLevelController.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockLevelService stockLevelService;

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
}
