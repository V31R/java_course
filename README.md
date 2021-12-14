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
