package pl.sek.nas.adapter;

import pl.sek.nas.port.ServiceRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryServiceRepository implements ServiceRepository {

    @Override
    public void saveService(String serviceName) {
        if(!InMemoryDatabase.DATABASE.containsKey(serviceName)) {
            InMemoryDatabase.DATABASE.put(serviceName,new LinkedList<>());
        }
    }

    @Override
    public List<String> getAllServices() {
        return new ArrayList<>(InMemoryDatabase.DATABASE.keySet());
    }
}
