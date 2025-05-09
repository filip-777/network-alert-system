package pl.sek.nas.domain;

import pl.sek.nas.port.DependencyRepository;
import pl.sek.nas.port.ServiceRepository;

import java.util.List;

class AlertNetworkImpl implements AlertNetwork {

    private final BFS bfs;
    private final DependencyRepository dependencyRepository;
    private final ServiceRepository serviceRepository;

    public AlertNetworkImpl(DependencyRepository dependencyRepository, ServiceRepository serviceRepository) {
        this.dependencyRepository = dependencyRepository;
        this.serviceRepository = serviceRepository;
        this.bfs = new BFS();
    }

    @Override
    public void addService(String service) {
        serviceRepository.saveService(service);
    }

    @Override
    public void addDependency(String fromService, String toService) {
        dependencyRepository.addDependencyServiceToService(fromService, toService);
    }

    @Override
    public List<String> getDependencies(String service) {
        return dependencyRepository.getDependencies(service);
    }

    @Override
    public List<String> findAlertPropagationPath(String source, String target) {
        loadDataFromInMemoryDB();
        return bfs.findAllServicesBetween(source, target);
    }


    @Override
    public List<String> getAffectedServices(String source) {
        loadDataFromInMemoryDB();
        return bfs.findAllAffectedServices(source);
    }

//    @Override
//    public List<Pair<String, String>> suggestContainmentEdges(String source) {
//        return List.of();
//    }

    private void loadDataFromInMemoryDB() {
        bfs.addAllDependencies(dependencyRepository.getAllDependencies());
        bfs.addServices(serviceRepository.getAllServices());
    }
}


