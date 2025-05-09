# Network-alert-system

This is a demo project how could be solved basic network alert system.
Dask description is in folder: [Open File](./task/Java_homework_assignments.pdf)

## API
To use NAS (Network Alert System) you need to use **AlertNetworkProxy**
In this class you have such methods:
- void addService(String service);
  - This method add service on which we later can add dependencies 
- void addDependency(String fromService, String toService); // Directed edge
  - This method ads dependencies to added to memory services
  - This method will throw NoSuchElementException when service to which we wish to add dependencies is not found
- List< String > getDependencies(String service);
  - This method returns list of dependencies for provided service 
- List < String > findAlertPropagationPath(String source, String target);
  - This method returns name list of services affected by alert between two chosen services
  - This method returns empty list if no services where affected
- List < String > getAffectedServices(String source);
  - This method returns all services which can be affected by service failure

## Tests
Test which show how system works are in AlertNetworkTest class

## Architecture
In this library is basic port-adapter architecture.
If in memory database need to be switched to another one please provide right adapter and update AlertNetworkProxy class with it.

## Algorithm
For methods `findAlertPropagationPath` and `getAffectedServices` is implemented basic BFS algorithm with **adjacency list**.
Algorithm is in class `BFS` in `domain` package.

# Local setup guide
## Git hooks
Git "run tests" hook is added to this project via git maven hook plugin.

For now, we only manage local githooks for precommit which will check **mvn verify** lifecycle.

Goal of such hook is to ensure basic code protection via executing tests before each commit.

To make githooks work propper change file [Java local example config](config/local_example/java_local_config.sh.example)
and move it to path `config/local`.

Ensure that execute rights are provided for script files. You can achieve that via those commands:
- `chmod +x java_local_config.sh`
- `chmod +x .git/hooks/pre-commit`
- `chmod +x git-hooks/pre-commit.sh`

## Code formating
For code formating is used [prettier plugin](https://github.com/HubSpot/prettier-maven-plugin?tab=readme-ov-file). Code check is performed during git pre-commit hook.

To update code you need manual trigger prettier plugin.

You can do it by triggering manual code format with command `./mvnw prettier:write`