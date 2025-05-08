package pl.sek.nas.port;

import java.util.List;

public interface ServiceRepository {
    void saveService(String serviceName);
    List<String> getAllServices();
}
