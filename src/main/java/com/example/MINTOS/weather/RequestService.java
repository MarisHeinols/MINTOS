package com.example.MINTOS.weather;

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
        System.out.println("this is 1");
        System.out.println(clientIpAddress);
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress = request.getHeader("Proxy-Client-IP");
            System.out.println("this is 2");
            System.out.println(clientIpAddress);
        }
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress =  request.getHeader("WL-Proxy-Client-IP");
            System.out.println("this is 3");
            System.out.println(clientIpAddress);
        }
        if(!StringUtils.hasLength(clientIpAddress) || "unknown".equals(clientIpAddress)){
            clientIpAddress = request.getRemoteAddr();
            System.out.println("this is 4");
            System.out.println(clientIpAddress);
            if(LOCALHOST_IPV4.equals(clientIpAddress)|| LOCALHOST_IPV6.equals(clientIpAddress)){
                try{
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    clientIpAddress = inetAddress.getHostAddress();
                    System.out.println("this is 5");
                    System.out.println(clientIpAddress);
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
