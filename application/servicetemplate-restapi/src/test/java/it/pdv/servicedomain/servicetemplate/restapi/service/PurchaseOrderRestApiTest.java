package it.pdv.servicedomain.servicetemplate.restapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder;
import it.pdv.servicedomain.servicetemplate.domain.entity.PurchaseOrder.Status;
import it.pdv.servicedomain.servicetemplate.domain.usecase.CreatePurchaseOrderUseCase;
import it.pdv.servicedomain.servicetemplate.restapi.PurchaseorderApiController;
import it.pdv.servicedomain.servicetemplate.restapi.configuration.OpenAPIDomainExceptionHandler;
import it.pdv.servicedomain.servicetemplate.restapi.configuration.OpenAPIExceptionHandler;
import it.pdv.servicedomain.servicetemplate.restapi.model.PurchaseOrderRequestOpenAPI;

public class PurchaseOrderRestApiTest {

	private MockMvc restEndPoint;
	private CreatePurchaseOrderUseCase createPurchaseOrderUseCase;

	@BeforeEach
	public void setUp() throws Exception {
		createPurchaseOrderUseCase = mock(CreatePurchaseOrderUseCase.class);
		;
		restEndPoint = MockMvcBuilders
				.standaloneSetup(
						new PurchaseorderApiController(new PurchaseOrderRestApiImpl(createPurchaseOrderUseCase)))
				.setControllerAdvice(OpenAPIExceptionHandler.class, OpenAPIDomainExceptionHandler.class)
				.build();
	}

	@Test
	public void createPurchaseOrderTest() throws Exception {
		PurchaseOrder purchaseOrder = new PurchaseOrder("code", Status.DRAFT, "customer", Instant.now());
		when(createPurchaseOrderUseCase.createPurchaseOrder(any())).thenReturn(purchaseOrder);

		restEndPoint
				.perform(post("/api/purchaseorder")
						.content(asJsonString(new PurchaseOrderRequestOpenAPI().customer("customer")))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(
						"{" + "	\"code\": \"code\"," + "	\"status\": \"DRAFT\"," + "	\"customer\": \"customer\","
								+ "	\"orderedAt\": null," + "	\"expectedDeliveryAt\": null" + "}"));
	}

	@Test
	public void createPurchaseOrderInvalidRequestTest() throws Exception {
		restEndPoint
				.perform(post("/api/purchaseorder").content(asJsonString(new PurchaseOrderRequestOpenAPI()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is(400));
	}

	@Test
	public void getPurchaseOrderTest() throws Exception {
		restEndPoint.perform(get("/api/purchaseorder/code").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().is5xxServerError());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
