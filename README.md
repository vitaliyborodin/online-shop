# Информационная система интернет магазина

## Роли

### Администратор
Создает/изменяет учетные записи пользователей. В системе всегда присутствует один пользователь с этой ролью. По умолчанию имя и пароль этого пользователя: admin/admin
Удалить эту учетную запись нельзя.

### Менеджер
Создает/изменяет:
* категории товаров
* товары
* заказы

### Клиент
Создает/изменяет заказы.

## Системы

### Система управления пользователями
Доступна пользователям с ролью 'администратор' и 'клиент'.
Система позволяет роли 'администратор':
* создать любой тип пользовательской учетной записи (account)
* включить/выключить учетную запись
* изменить учетную запись.
Система позволяет роли 'клиент':
* Создать/изменить клиенту свою учетную запись

### Система управления товарами
Доступна только ролям 'администратор' и 'менеджер'. Система позволяет:
* создавать и изменять категории товаров (Пример 1: Товары для авто -> Автохимия -> Средства для ухода за салоном. Пример 2: Детские товары -> Средства гигиены -> Подгузники)
* создавать и изменять записи для каждого товара (Артикул, наименование, производитель, описание, кол-во единиц товара на складе, загружать изображения)
* История изменений сохраняется.

### Система управления заказами
Доступна всем типам пользователей, а также незарегестрированным пользователям.
Клиент без регистрации(идентифицируются по номеру телефона) может создать заказ, но не может его изменять.
Клиент с регистрацией может просматривать статус своих заказов.
Менеджер и администратор и могут:
* просматривать заказы всех клиентов
* обновлять  статус заказов
* отменять заказы