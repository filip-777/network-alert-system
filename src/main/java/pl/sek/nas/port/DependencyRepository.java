package pl.sek.nas.port;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface DependencyRepository {
  void addDependencyServiceToService(String serviceName, String dependencyName);

  List<String> getDependencies(String serviceName);

  HashMap<String, LinkedList<String>> getAllDependencies();
}
