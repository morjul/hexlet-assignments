# Связь один-ко-многим

Вам необходимо разработать простую систему управления задачами для команды разработчиков. Каждая задача имеет название, описание и ответственного разработчика. У каждого разработчика может быть несколько задач, а каждая задача может быть привязана только к одному разработчику. Вам предстоит реализовать полный CRUD задачи

## Ссылки

* [https://github.com/hexlet-components/java-spring-blog/tree/main](Пример приложения на Spring Boot)

## src/main/java/exercise/model/User.java, Task.java

## Задачи

Задайте в моделях связь между сущностями один-ко-многим. Заглядывайте в DTO классы, чтобы уточнить интерфейс приложения. Поле модели задач с разработчиком, на которого назначена задача, должно называться *assignee*

## src/main/java/exercise/mapper/TaskMapper.java

## Задачи

Реализуйте маппер для автоматического преобразования DTO в сущность и обратно

## src/main/java/exercise/controller/TaskController.java

## Задачи

Реализуйте полный CRUD для сущности задач. Создайте обработчики для просмотра списка всех задач и конкретной задачи, создания, обновления и удаления задачи:

* *GET /tasks* – просмотр списка всех задач
* *GET /tasks/{id}* – просмотр конкретной задачи
* *POST /tasks* – создание новой задачи
* *PUT /tasks/{id}* – редактирование задачи. При редактировании м должны иметь возможность поменять название, описание задачи и ответственного разработчика
* *DELETE /tasks* – удаление задачи

## Подсказки

* Если при решении возникнут сложности, заглядывайте в наш [эталонный репозиторий](https://github.com/hexlet-components/java-spring-blog/tree/main)