[![codecov](https://codecov.io/gh/V31R/java_course/branch/master/graph/badge.svg?token=4FF2N6MU0L)](https://codecov.io/gh/V31R/java_course)
[![Java CI with Maven](https://github.com/V31R/java_course/actions/workflows/maven.yml/badge.svg)](https://github.com/V31R/java_course/actions/workflows/maven.yml)


# To Do list

[Описание API в Swagger/Open API](https://app.swaggerhub.com/apis/V31R/ToDoList/1.0.0#/)

# To Do list

Для обеспечения работы программы было выделено 3 основных класса:
1. Task - класс команды.
2. TaskList - содержит в себе сами задачи.
3. TaskController - rest controller

## Пути запросов:
	GET/tasks - получение списка задач.
	GET/tasks/{id} - получение задачи с идентификатором id.
	DELETE/tasks/{id} - удаление задачи с идентификатором id.
	PATCH/tasks/{id} - Изменение задачи с идентификатором id.
	POST/tasks/{description} - добавление задачи с описанием description.
