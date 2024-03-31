import java.rmi.*;
import java.util.List;

public interface TaskServer extends Remote {
    void addTask(String task) throws RemoteException;
    void removeTask(String task) throws RemoteException;

    List<String> getAllTasks() throws RemoteException;
}
