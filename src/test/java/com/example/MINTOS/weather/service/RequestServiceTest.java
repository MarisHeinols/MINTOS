package com.example.MINTOS.weather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {RequestService.class})
@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    private RequestService requestService;

    @BeforeEach
    void setUp() {
        requestService = new RequestService();
    }

    @Test
    void getRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Forwarder-For","192.0.0.0");
        assertEquals("192.0.0.0",requestService.getClientsIpAddress(request));

    }
    @Test
    void getRequestNoHeader() throws UnknownHostException {
        String localAddress = Inet4Address.getLocalHost().getHostAddress();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String ipTested = requestService.getClientsIpAddress(request);
        assertEquals(localAddress,ipTested);

    }
}
