package common;

import client.ClientImpl;

import java.rmi.Remote;

public interface IGui extends Remote {
    void setCtrl(ClientImpl ctrl);
}