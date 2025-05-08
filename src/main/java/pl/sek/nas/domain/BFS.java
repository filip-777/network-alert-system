package pl.sek.nas.domain;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BFS {

    private final static String QUEUE_START = "START";
    private final HashMap<String, LinkedList<String>> adjacencyList;

    public BFS() {
        adjacencyList = new HashMap<>();
    }

    private void addService(String service) {
        if (!adjacencyList.containsKey(service)) {
            adjacencyList.put(service, new LinkedList<>());
        }
    }

    public void addServices(List<String> services) {
        services.forEach(this::addService);
    }

    private void addDependency(String service, String serviceDependency) {
        addService(service);
        addService(serviceDependency);
        adjacencyList.get(service).add(serviceDependency);
    }

    public void addAllDependencies(HashMap<String, LinkedList<String>> dependencies) {
        dependencies.keySet().forEach(service ->
                dependencies.get(service).forEach(serviceDependency ->
                        addDependency(service, serviceDependency)
                )
        );
    }

    public List<String> findAllServicesBetween(String startService, String endService) {
        HashMap<String, Boolean> visited = markAsNotVisited();

        Queue<String> queue = new LinkedList<>();

        HashMap<String, String> previous = new HashMap<>();

        visited.put(startService, true);
        queue.offer(startService);
        previous.put(startService, QUEUE_START);

        while (!queue.isEmpty()) {
            String servicePolled = queue.poll();

            if (servicePolled.equals(endService)) {
                return reverseToGetPath(previous, endService);
            }

            adjacencyList.get(servicePolled)
                    .stream()
                    .filter(ifServiceVisited(visited))
                    .forEach(adjacentService -> {
                        visited.put(adjacentService, true);
                        queue.offer(adjacentService);
                        previous.put(adjacentService, servicePolled);
                    });
        }

        return Collections.emptyList();
    }

    public List<String> findAllAffectedServices(String startService) {
        HashMap<String, Boolean> visited = markAsNotVisited();
        Queue<String> queue = new LinkedList<>();
        List<String> reachedServices = new ArrayList<>();
        visited.put(startService, true);
        queue.offer(startService);

        while (!queue.isEmpty()) {
            String servicePolled = queue.poll();

            adjacencyList.get(servicePolled)
                    .stream()
                    .filter(ifServiceVisited(visited))
                    .forEach(adjacentService -> {
                        visited.put(adjacentService, true);
                        queue.offer(adjacentService);
                        reachedServices.add(adjacentService);
                    });
        }
        return orderList(reachedServices);
    }

    private static List<String> orderList(List<String> reachedServices) {
        return reachedServices.stream().sorted().collect(Collectors.toList());
    }

    private static Predicate<String> ifServiceVisited(HashMap<String, Boolean> visited) {
        return adjacentService -> !visited.get(adjacentService);
    }

    private HashMap<String, Boolean> markAsNotVisited() {
        HashMap<String, Boolean> visited = new HashMap<>();
        adjacencyList.keySet().forEach(service -> visited.put(service, false));
        return visited;
    }

    private List<String> reverseToGetPath(HashMap<String, String> previousLinks, String endService) {
        List<String> path = new ArrayList<>();
        String current = endService;

        while (!current.equals(QUEUE_START)) {
            path.add(current);
            current = previousLinks.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}

