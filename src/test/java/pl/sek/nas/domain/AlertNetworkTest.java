package pl.sek.nas.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sek.nas.adapter.InMemoryDependencyRepository;
import pl.sek.nas.adapter.InMemoryServiceRepository;

class AlertNetworkTest {
  private final InMemoryServiceRepository inMemoryServiceRepository = new InMemoryServiceRepository();
  private final InMemoryDependencyRepository inMemoryDependencyRepository = new InMemoryDependencyRepository();

  private final AlertNetwork alertNetwork = new AlertNetworkImpl(
    inMemoryDependencyRepository,
    inMemoryServiceRepository
  );

  private final List<String> SERVICE_NAMES = List.of(
    "A",
    "B",
    "C",
    "D",
    "E",
    "G",
    "H"
  );

  @BeforeEach
  void setUp() {
    SERVICE_NAMES.forEach(alertNetwork::addService);
  }

  @Test
  void addService() {
    //then
    Assertions.assertEquals(
      inMemoryServiceRepository.getAllServices().size(),
      SERVICE_NAMES.size()
    );
  }

  @Test
  void should_add_dependency() {
    //given
    alertNetwork.addDependency("A", "B");
    alertNetwork.addDependency("B", "C");
    alertNetwork.addDependency("A", "D");
    alertNetwork.addDependency("D", "C");
    //when
    HashMap<String, LinkedList<String>> allDependencies = inMemoryDependencyRepository.getAllDependencies();
    //then
    Assertions.assertEquals(allDependencies.size(), SERVICE_NAMES.size());
  }

  @Test
  void should_get_dependencies() {
    //given
    alertNetwork.addDependency("A", "B");
    alertNetwork.addDependency("A", "D");
    alertNetwork.addDependency("D", "C");
    //when
    List<String> dependenciesA = alertNetwork.getDependencies("A");
    List<String> dependenciesB = alertNetwork.getDependencies("B");
    List<String> dependenciesD = alertNetwork.getDependencies("D");
    //then
    Assertions.assertEquals(2, dependenciesA.size());
    Assertions.assertEquals("B", dependenciesA.getFirst());
    Assertions.assertEquals("D", dependenciesA.getLast());
    Assertions.assertEquals(0, dependenciesB.size());
    Assertions.assertEquals("C", dependenciesD.getFirst());
  }

  @Test
  void should_find_alert_propagation_path() {
    //given
    alertNetwork.addDependency("A", "B");
    alertNetwork.addDependency("B", "C");
    alertNetwork.addDependency("A", "D");
    alertNetwork.addDependency("D", "C");
    //when
    List<String> alertPropagationPath = alertNetwork.findAlertPropagationPath(
      "A",
      "C"
    );
    //then
    Assertions.assertEquals(3, alertPropagationPath.size());
    Assertions.assertEquals(List.of("A", "B", "C"), alertPropagationPath);
  }

  @Test
  void should_not_find_alert_propagation_path() {
    //given
    alertNetwork.addDependency("A", "B");
    alertNetwork.addDependency("A", "D");
    alertNetwork.addDependency("E", "G");
    alertNetwork.addDependency("G", "H");
    //when
    List<String> alertPropagationPath = alertNetwork.findAlertPropagationPath(
      "A",
      "H"
    );
    //then
    Assertions.assertEquals(0, alertPropagationPath.size());
  }

  @Test
  void should_get_affected_services() {
    //given
    alertNetwork.addDependency("A", "B");
    alertNetwork.addDependency("B", "C");
    alertNetwork.addDependency("A", "D");
    alertNetwork.addDependency("D", "C");
    //when
    List<String> affectedServices = alertNetwork.getAffectedServices("A");
    //then
    Assertions.assertEquals(3, affectedServices.size());
    Assertions.assertEquals(List.of("B", "C", "D"), affectedServices);
  }
  //    @Test
  //    void suggestContainmentEdges() {
  //    }

}
