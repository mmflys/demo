package com.github.mmflys.demo.cling;

import org.testng.annotations.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;

import static java.lang.System.out;

public class QuickTest {

    public static void main(String[] args) {
        Path root = Paths.get("/");
        System.out.println(root.getParent());
        System.out.println(root.getFileName());
    }

    static void displayInterfaceInformation(NetworkInterface netint) {
        out.printf("Display name: %s\n", netint.getDisplayName());
        out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            out.printf("InetAddress: %s\n", inetAddress);
        }
        out.printf("\n");
    }

    @Test
    public synchronized static Inet4Address getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress instanceof Inet4Address) {
                            return ((Inet4Address) inetAddress);
                        }
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

}
