import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.List;

public class TaskServerImpl extends UnicastRemoteObject implements TaskServer {
    private List<String> taskList;

    public TaskServerImpl() throws RemoteException {
        taskList = new ArrayList<>();
    }

    @Override
    public void addTask(String task) throws RemoteException {
        taskList.add(task);
    }

    @Override
    public List<String> getAllTasks() throws RemoteException {
        return taskList;
    }

    @Override
    public void removeTask(String task) throws RemoteException {
        taskList.remove(task);
    }

    public static void main(String[] args) {
        try {
            TaskServerImpl obj = new TaskServerImpl();
            Registry registry = LocateRegistry.createRegistry(19);
            // Bind the server to the RMI registry with name "TaskServer"
            registry.rebind("TaskServer", obj);
            System.out.println("TaskServer is listening");
        } catch (Exception e) {
            System.err.println("TaskServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
