package com.gestion.Client;

import com.settings.IDAO;

import java.util.List;

public interface IClientDAO extends IDAO<Client> {
    List<Client> findall(String key);
    List<Client> findallLocal(String key, List<Client> clientList);
    List<Client> findallByVilleId(long id);
}
