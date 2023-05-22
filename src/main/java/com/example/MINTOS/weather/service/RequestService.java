package com.example.MINTOS.weather.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class RequestService {
    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    public String getClientsIpAddress(HttpServletRequest request){
        String clientIpAddress = request.getHeader("X-Forwarder-For");
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress = request.getHeader("Proxy-Client-IP");
        }
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress =  request.getHeader("WL-Proxy-Client-IP");
        }
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(clientIpAddress)|| LOCALHOST_IPV6.equals(clientIpAddress)){
                try{
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    clientIpAddress = inetAddress.getHostAddress();
                }
                catch (UnknownHostException e){
                    e.printStackTrace();
                }
            }
        }
        if(StringUtils.hasLength(clientIpAddress) && clientIpAddress.length() > 15
                && clientIpAddress.indexOf(",") > 0){
            clientIpAddress = clientIpAddress.substring(0, clientIpAddress.indexOf(","));
        }
        return clientIpAddress;
    };
}
