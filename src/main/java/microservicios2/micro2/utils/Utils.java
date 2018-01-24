package microservicios2.micro2.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {

    public static String getFirstNonLoopbackAddress() throws SocketException {
        Enumeration enumerationInterfaces = NetworkInterface.getNetworkInterfaces();
        while (enumerationInterfaces.hasMoreElements()) {
            NetworkInterface anInterface = (NetworkInterface) enumerationInterfaces.nextElement();
            for (Enumeration enumerationAddresses = anInterface.getInetAddresses(); enumerationAddresses.hasMoreElements();) {
                InetAddress addr = (InetAddress) enumerationAddresses.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        return addr.toString();
                    }

                }
            }
        }
        return null;
    }
}
