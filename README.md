# Network-alert-system
This is a demo project how could be solved basic network alert system.
Dask description is in folder: [Open File](./task/Java_homework_assignments.pdf)

# Git hooks
Git test hook is added to this project via git maven hook plugin.

For now, we only manage local githooks for precommit which will check mvn test lifecycle.
Goal of such hook is to ensure basic code protection via executing tests
To make githooks work propper change file [Java local example config](config/local_example/java_local_config.sh.example)
and move it to path config/local.

Ensure that execute rights are provided for script files. You can achieve that via those commands:
`chmod +x java_local_config.sh`
`chmod +x .git/hooks/pre-commit`
`chmod +x git-hooks/pre-commit.sh`

# Code formating
For code formating is used [prettier plugin](https://github.com/HubSpot/prettier-maven-plugin?tab=readme-ov-file)
Code is formated by this tool during pre-commit hook