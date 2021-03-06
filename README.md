Spring Boot + Mongodb + Spring Cloud Stream + Gradle
=========================

***

Текст задания:
------------

Написать микросервис для выбора капитана команды

Бизнес логика.

Участники должны определить, кто в их команде капитан. Для этого у всех членов команды на экране появляется надпись "Если вы хотите быть капитаном, нажмите кнопку" и кнопка "Я - капитан". Как только один из команды нажал, у всех остальных появляется надпись "Капитан выбран. Это - Иванов Иван", кнопка исчезает. У капитана же появляется надпись "Вы - капитан" и кнопка "Отказаться".

Что требуется от бека:

    Принять get запрос, содержащий teamId и ответить, есть ли у команды капитан и, если есть, то кто (participantId и participantIdentifier)
    Принять post запрос на то, чтобы стать капитаном, содержащий teamId, participantId и participantIdentifier. Если у команды есть капитан, то вернуть { success: false }. Если нет, то сохранить настройки для команды в mongodb и опубликовать сообщение, что капитан для команды назначен в rabbitmq.
    Принять post запрос на отказ быть капитаном, сохранить все в бд и опубликовать сообщение об этом.


Если не знакомы с rabbitmq, ничего страшного, просто напишите сервис, который вместо публикации сообщения будет писать в консоль объект этого сообщения.

Фронт писать не нужно, только бэк.

participantIdentifier - это идентификатор участника. Обычно это будет фамилия имя.

Для реализации обязательно использовать

    Spring boot web (>2.0.0)
    Mongodb (>3.6)
    Gitlab/github для демонстрации кода (мой ник asserebryanskiy, просто добавьте меня в репо)
    Gradle для сборки


Будет плюсом:

    учесть, что два участника могут отправить запрос на становление капитаном одновременно, но капитаном может стать только один - тот, кто отправил запрос первым
    написать юнит тесты для бизнес логики
    Написать интеграционный тест


Docker start
-----------
1. Run Mongodb and rabbitmq
2. Configure all params in [env](env) file
3. Run project: `docker run --name captain-latest --env-file env --net="host" -d docker4tarasov/captain`
