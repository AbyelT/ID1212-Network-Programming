package natverkprogg.labb5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class RmiClient {
    public static void main(String args[]) throws Exception {
        RmiServerInf server = (RmiServerInf)Naming.lookup("//localhost/RmiServer");
        System.out.println(server.getMessage());
    }
}