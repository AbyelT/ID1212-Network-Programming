package natverkprogg.labb5;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInf extends Remote {
    String getMessage() throws RemoteException;
}
