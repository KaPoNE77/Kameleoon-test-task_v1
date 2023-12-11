package com.example.Kameleoontesttask_v1.service;

import com.example.Kameleoontesttask_v1.model.Client;
import com.example.Kameleoontesttask_v1.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сейчас в роли хранилища клиентов будет выступать Map<Integer, Client>. Ключом карты будет id клиента, а значением — сам клиент. Сделано это для того, чтобы не перегружать пример спецификой работы с БД.
 */

@Service
public class ClientServiceImpl implements ClientService { // когда имплементировался - сразу появился код с заголовками @Override
    /**
     * Аннотация @Service говорит спрингу, что данный класс является сервисом. Это специальный тип классов, в котором реализуется некоторая
     * бизнес логика приложения. Впоследствии, благодаря этой аннотации Spring будет предоставлять нам экземпляр данного класса в местах,
     * где это, нужно с помощью Dependency Injection.
     */

    // Хранилище клиентов
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void create(Client client) {
        clientRepository.save(client);
    }
    @Override
    public List<Client> readAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client read(int id) {
        return clientRepository.getOne(id);
    }

    @Override
    public boolean update(Client client, int id) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            clientRepository.save(client);
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(int id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
