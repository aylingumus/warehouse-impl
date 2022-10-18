package warehouseapp.controller

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class, MockitoExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class ProductControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    @Order(1)
    fun `should return all available products`() {
        mvc.perform(
            MockMvcRequestBuilders
                .get("/warehouse/available-products")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty)
    }

    @Test
    @Order(5)
    fun `should return NO CONTENT(204) if there is no available product`() {
        mvc.perform(
            MockMvcRequestBuilders
                .get("/warehouse/available-products")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isNoContent)
            .andExpect(content().string("No available products."))
    }

    @RepeatedTest(2)
    @Order(2)
    fun `should sell product and decrease articles from inventory`(repetitionInfo: RepetitionInfo) {
        val productId = 1
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/warehouse/sell-product")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .param("id", productId.toString())
        if (repetitionInfo.currentRepetition == 1) {
            mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.outOfStock").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.containArticles").isNotEmpty)
        } else {
            mvc.perform(builder)
                .andDo(print())
                .andExpect(status().isNoContent)
        }
    }

    @Test
    @Order(3)
    fun `should return 'out of stock' when selling a product`() {
        val productId = 1
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/warehouse/sell-product")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .param("id", productId.toString())
        mvc.perform(builder)
            .andDo(print())
            .andExpect(status().isNoContent)
            .andExpect(content().string("Out of stock."))
    }

    @Test
    @Order(4)
    fun `should return 'product not found' when selling a product`() {
        val productId = 3
        val builder: MockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/warehouse/sell-product")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .param("id", productId.toString())
        mvc.perform(builder)
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().string("Product not found."))
    }
}
