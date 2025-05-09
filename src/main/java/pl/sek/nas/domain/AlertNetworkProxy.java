package pl.sek.nas.domain;

import java.util.List;
import pl.sek.nas.adapter.InMemoryDependencyRepository;
import pl.sek.nas.adapter.InMemoryServiceRepository;

public class AlertNetworkProxy implements AlertNetwork {
  private final InMemoryServiceRepository inMemoryServiceRepository = new InMemoryServiceRepository();
  private final InMemoryDependencyRepository inMemoryDependencyRepository = new InMemoryDependencyRepository();

  private final AlertNetwork alertNetwork = new AlertNetworkImpl(
    inMemoryDependencyRepository,
    inMemoryServiceRepository
  );

  @Override
  public void addService(String service) {
    alertNetwork.addService(service);
  }

  @Override
  public void addDependency(String fromService, String toService) {
    alertNetwork.addDependency(fromService, toService);
  }

  @Override
  public List<String> getDependencies(String service) {
    return alertNetwork.getDependencies(service);
  }

  @Override
  public List<String> findAlertPropagationPath(String source, String target) {
    return alertNetwork.findAlertPropagationPath(source, target);
  }

  @Override
  public List<String> getAffectedServices(String source) {
    return alertNetwork.getAffectedServices(source);
  }
}
