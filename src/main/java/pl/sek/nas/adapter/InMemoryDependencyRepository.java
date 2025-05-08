package pl.sek.nas.adapter;

import pl.sek.nas.port.DependencyRepository;

import java.util.*;

public class InMemoryDependencyRepository implements DependencyRepository  {

    @Override
    public void addDependencyServiceToService(String serviceName, String dependencyName) {
        if (InMemoryDatabase.DATABASE.containsKey(serviceName)) {

            boolean filteredDependencyName = InMemoryDatabase.DATABASE.get(serviceName).stream()
                    .filter(sn -> sn.equals(dependencyName))
                    .findAny()
                    .isEmpty();
            if (filteredDependencyName) {
                InMemoryDatabase.DATABASE.get(serviceName).add(dependencyName);

            }

        } else {
            throw new NoSuchElementException("There is no service with name " + serviceName + " to which can be added to the dependency service");
        }
    }

    @Override
    public List<String> getDependencies(String serviceName) {
        return Optional.ofNullable(InMemoryDatabase.DATABASE.get(serviceName)).orElseGet(LinkedList::new);
    }

    @Override
    public HashMap<String, LinkedList<String>> getAllDependencies() {
        return InMemoryDatabase.DATABASE;
    }
}
