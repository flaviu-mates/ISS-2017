package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientController extends Remote
{
    void showUpdated() throws RemoteException;
}
