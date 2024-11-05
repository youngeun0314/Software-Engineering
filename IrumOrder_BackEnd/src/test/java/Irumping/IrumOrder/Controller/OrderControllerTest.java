package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOrder() throws Exception {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setUserId(1);
        orderRequest.setTotalPrice(10000);
        orderRequest.setOrderMenuOptions(Collections.singletonList(new OrderMenuDto()));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        int orderId = 1;
        String newStatus = "주문접수";

        mockMvc.perform(put("/orders/{orderId}/status", orderId)
                        .param("status", newStatus))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePaymentStatus() throws Exception {
        int orderId = 1;

        mockMvc.perform(put("/orders/{orderId}/payment", orderId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserOrderHistory() throws Exception {
        int userId = 1;

        mockMvc.perform(get("/orders/user/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOrdersByPickUpTime() throws Exception {
        String pickUpTime = "12:00:00";

        mockMvc.perform(get("/orders/pickup")
                        .param("time", pickUpTime))
                .andExpect(status().isOk());
    }
}
