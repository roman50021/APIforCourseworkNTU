# APIforCourseworkNTU

## Тематика курсової роботи
21. Система онлайн-замовлення їжі для ресторанного бізнесу

## Клонування та Запуск Проекту

### Клонування через командний рядок

1. Відкрийте термінал або командний рядок на вашому комп'ютері.

2. Використовуючи команду git clone, склонуйте репозиторій на свій комп'ютер:

   ```bash
   git clone https://github.com/your-username/APIforCourseworkNTU.git
3. Перейдіть до папки проекту:
   ```bash
   cd APIforCourseworkNTU
### Клонування через IDE
Відкрийте вашу улюблену IDE та скористайтеся можливістю клонування репозиторію
за допомогою його URL: https://github.com/roman50021/APIforCourseworkNTU.git
## Запуск проекту
1. Запуск через командний рядок
2. Відкрийте термінал або командний рядок.
3. Налаштування файлу application.properties
```bash
#db related settings
spring.datasource.url=jdbc:mysql://localhost:3306/DatabaseforCourseworkNTU
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
   Перейдіть до папки проекту:
   ```bash
   cd APIforCourseworkNTU
   ```

3. Виконайте команди для ініціалізації та запуску:
```bash
npm install  # або інша команда для встановлення залежностей
npm start    # або інша команда для запуску проекту
```

## Запуск через IDE
1. Відкрийте ваш проект у IDE.
2. Налаштування файлу application.properties

```bash
#db related settings
spring.datasource.url=jdbc:mysql://localhost:3306/DatabaseforCourseworkNTU
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
3. Використайте вбудовані інструменти для встановлення залежностей та запуску проекту.

# Швидка перевірка
## Авторизація та реєстрація
### Реєстрація
URL для перевірки (POST):
   ```bash
    http://localhost:8080/api/v1/auth/register
   ```
JSON для перевірки:
```bash
{
   "email": "user@gamil.com",
   "firstname": "User",
   "lastname": "User",
   "password": "123"
}
```
Отримаємо відповідь:

![img.png](src/main/resources/imgs/img.png)

Якщо спробуємо повторно створити цього ж користувача, отримаємо таку відповідь:

![img_1.png](src/main/resources/imgs/img_1.png)

За стандартом створюватиметься користувач за ролью User.

### Авторизація
Спробуємо авторизуватися користувача, для якого ми тільки що створили обліковий запис.

URL для перевірки (POST):

   ```bash
    http://localhost:8080/api/v1/auth/authenticate
   ```

JSON для перевірки:

```bash
{
  "email": "user1@gamil.com",
  "password": "123"
  // user
}
   ```
Отримаємо відповідь, та токен для подальших дій:

![img_2.png](src/main/resources/imgs/img_2.png)

Але якщо вести неправильний e-mail або пароль отримаємо таку відповідь:

![img_3.png](src/main/resources/imgs/img_3.png)

## Функції User для Order

### Створення Order
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/user/create
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
  "email": "user1@gamil.com",
  "address": "123 Main St",
  "menuIds": [52],  // Список идентификаторов блюд
  "paid": true
}
```
### Отримання Orders
URL для перевірки (GET):
   ```bash
http://localhost:8080/api/v1/user/orders
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```
JSON для перевірки:
```bash
{
    "email": "user1@gamil.com"
}
```
Відповідь: 
```bash
{
    "message": "User orders retrieved successfully",
    "orders": [
        {
            "id": 652,
            "user": {
                "id": 752,
                "firstname": "User",
                "lastname": "User",
                "email": "user1@gamil.com",
                "password": "$2a$10$Zx5gPgu8IxPq29rWOVQdROOrbyihpNvbyIiue07Ifvl.VXrHRylQG",
                "role": "USER",
                "enabled": true,
                "username": "user1@gamil.com",
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "accountNonLocked": true,
                "credentialsNonExpired": true,
                "accountNonExpired": true
            },
            "email": "user1@gamil.com",
            "address": "123 Main St",
            "menuIds": [
                52
            ],
            "menuNames": [
                "Margherita Pizza"
            ],
            "paid": true,
            "status": "PROCESSING",
            "totalPrice": 14.99
        }
    ]
}
```



### Зміни Orders
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/user/change
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
  "email": "user1@gamil.com",
  "id": 652,
  "address": "123 Main St",
  "menuIds": [52, 56, 57],  // Список идентификаторов блюд
  "paid": true
}
```
Результат  http://localhost:8080/api/v1/user/orders:
```bash
{
    "message": "User orders retrieved successfully",
    "orders": [
        {
            "id": 652,
            "user": {
                "id": 752,
                "firstname": "User",
                "lastname": "User",
                "email": "user1@gamil.com",
                "password": "$2a$10$Zx5gPgu8IxPq29rWOVQdROOrbyihpNvbyIiue07Ifvl.VXrHRylQG",
                "role": "USER",
                "enabled": true,
                "username": "user1@gamil.com",
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "accountNonLocked": true,
                "credentialsNonExpired": true,
                "accountNonExpired": true
            },
            "email": "user1@gamil.com",
            "address": "123 Main St",
            "menuIds": [
                52,
                56,
                57
            ],
            "menuNames": [
                "Margherita Pizza",
                "Caprese Salad",
                "Shrimp Pad Thai"
            ],
            "paid": true,
            "status": "PROCESSING",
            "totalPrice": 41.47
        }
    ]
}
```

### Відміна Order
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/user/cancel
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
    "email": "user1@gamil.com",
    "id": "652"
}
```
Результат  http://localhost:8080/api/v1/user/orders:
```bash
{
    "message": "User orders retrieved successfully",
    "orders": []
}
```

### Видалення облікового запису користувача
URL для перевірки (DELETE):
   ```bash
http://localhost:8080/api/v1/user/delete
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
    "email": "user1@gamil.com"
}
```
## Функції Admin для User

Під час запуску програми буде автоматично cтворюватися користувач адміністратор.
Нам буде достатньо лише авторизуватись за цим e-mail та паролем( http://localhost:8080/api/v1/auth/authenticate):
```bash
{
    "email": "admin@example.com",
    "password": "admin"
}
```
### Отримання Users
URL для перевірки (GET):
   ```bash
http://localhost:8080/api/v1/admin/users/all
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIkFETUlOIl0sInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyNDY2NTY4LCJleHAiOjE3MDI0NjgwMDh9.0gLyi33b2sTRqm4kcd9PT5jFlQciCxZT4RYra8iRaoY
```
Відповідь:
```bash
[
    {
        "id": 52,
        "firstname": "Roman",
        "lastname": "Fedko",
        "email": "fedkoroma2@gamil.com",
        "password": "$2a$10$R7uZwFgkj30Ry/mNyFfrx.0jUDblVL8qwHT6S0KZf19K9Y5qQbRxW",
        "role": "ADMIN",
        "enabled": true,
        "authorities": [
            {
                "authority": "ADMIN"
            }
        ],
        "username": "fedkoroma2@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 357,
        "firstname": "Bober",
        "lastname": "1",
        "email": "bober5@gamil.com",
        "password": "$2a$10$jY5MH0UEHL7sYM4EQxpVruuzTMnNBjejJGKJ0CUNyni0qNMqalThq",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "bober5@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 358,
        "firstname": "Bober",
        "lastname": "2",
        "email": "bober2@gamil.com",
        "password": "$2a$10$PJzz/kbu22cvV8wIVDeW9e0QheW.ZPZnrfgacMIyCKux6tqUfKvF.",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "bober2@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 359,
        "firstname": "Bober",
        "lastname": "3",
        "email": "bober3@gamil.com",
        "password": "$2a$10$xziRMcXGRxFpyJTz21qU1ecm/OJCc5Z0nhHa1ndYUcAii8nqFQhAG",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "bober3@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 360,
        "firstname": "Bober",
        "lastname": "4",
        "email": "bober4@gamil.com",
        "password": "$2a$10$n0RoKQ6SEtk3i1TFSBtVBOIYg8Z9.fNpZiwprSHuO11DhnrdwuthO",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "bober4@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 361,
        "firstname": "Bober",
        "lastname": "6",
        "email": "bober6@gamil.com",
        "password": "$2a$10$8GbfCHteyDCh/5PIdYl9jerW4FgsU46W/a76X1oJyxU92FFVAac6u",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "bober6@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 402,
        "firstname": "Boris",
        "lastname": "Bober",
        "email": "boberboris@gamil.com",
        "password": "$2a$10$xdhWATYhfgtP/pmtaYYzhOEtF0czdZJM6kJ9c/EMH2Gg0hY93d7k2",
        "role": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "username": "boberboris@gamil.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    },
    {
        "id": 702,
        "firstname": "Admin",
        "lastname": "Admin",
        "email": "admin@example.com",
        "password": "$2a$10$kbhpssYwjkJGSUoJoH2CvuRXefzGZs47p6tj6armD4GSIRN2/uNpO",
        "role": "ADMIN",
        "enabled": true,
        "authorities": [
            {
                "authority": "ADMIN"
            }
        ],
        "username": "admin@example.com",
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true
    }
]
```

### Отримання User
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/admin/users/get
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIkFETUlOIl0sInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyNDY3Mjk0LCJleHAiOjE3MDI0Njg3MzR9.aTeS_v-2XxG6_T7JszAOH-WnrTkNumZbFexYxpo2VbE
```

JSON для перевірки:
```bash
{
   "email": "user@gamil.com",
   "firstname": "User",
   "lastname": "User"
}
```

### Зміни User
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/admin/users/update
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIkFETUlOIl0sInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyNDY3Mjk0LCJleHAiOjE3MDI0Njg3MzR9.aTeS_v-2XxG6_T7JszAOH-WnrTkNumZbFexYxpo2VbE
```

JSON для перевірки:
```bash
{
   "email": "user@gamil.com",
   "firstname": "updatedUser",
   "lastname": "updatedUser",
   "role" : "USER"
}
```

### Видалення облікового запису користувача через Admin
URL для перевірки (DELETE):
   ```bash
http://localhost:8080/api/v1/admin/users/delete
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
   "email": "user@gamil.com"
}
```

## Функції Admin для Menu

Під час запуску програми буде автоматично cтворюватися користувач адміністратор.
Нам буде достатньо лише авторизуватись за цим e-mail та паролем( http://localhost:8080/api/v1/auth/authenticate):
```bash
{
    "email": "admin@example.com",
    "password": "admin"
}
```

### Створення страви(Додавання страви до нашого меню)
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/admin/menu/add
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
    "name": "Chicken Tikka Masala",
    "description": "Tender pieces of chicken marinated in a spiced yogurt mixture, grilled to perfection, and served in a creamy tomato-based curry sauce",
    "servingWeight": 400.0,
    "price": 16.99
}
```

### Отримання страви
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/admin/menu/get
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
    "id": 102
}
```
Відповідь:
```bash
{
    "id": 102,
    "name": "Chicken Tikka Masala",
    "description": "Tender pieces of chicken marinated in a spiced yogurt mixture, grilled to perfection, and served in a creamy tomato-based curry sauce",
    "servingWeight": 400.0,
    "price": 16.99,
    "message": "We have this in menu!"
}
```

### Отримання страв
URL для перевірки (GET):
   ```bash
http://localhost:8080/api/v1/admin/menu/all
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```
Відповідь:
```bash
[
    {
        "id": 1,
        "name": "Chicken Caesar Salad",
        "description": "Caesar salad with grilled chicken breast",
        "servingWeight": 300.0,
        "price": 11.99
    },
    {
        "id": 52,
        "name": "Margherita Pizza",
        "description": "Traditional Italian pizza with tomatoes and mozzarella",
        "servingWeight": 400.0,
        "price": 14.99
    },
    {
        "id": 53,
        "name": "Grilled Salmon",
        "description": "Salmon fillet seasoned and grilled to perfection",
        "servingWeight": 250.0,
        "price": 17.99
    },
    {
        "id": 54,
        "name": "Vegetarian Stir-Fry",
        "description": "Fresh vegetables stir-fried with tofu in a savory sauce",
        "servingWeight": 220.0,
        "price": 10.99
    },
    {
        "id": 55,
        "name": "Beef Tacos",
        "description": "Soft corn tortillas filled with seasoned beef, lettuce, and cheese",
        "servingWeight": 180.0,
        "price": 8.99
    },
    {
        "id": 56,
        "name": "Caprese Salad",
        "description": "Classic Italian salad with tomatoes, fresh mozzarella, and basil",
        "servingWeight": 150.0,
        "price": 11.49
    },
    {
        "id": 57,
        "name": "Shrimp Pad Thai",
        "description": "Thai stir-fried rice noodles with shrimp, tofu, and peanuts",
        "servingWeight": 300.0,
        "price": 14.99
    },
    {
        "id": 102,
        "name": "Chicken Tikka Masala",
        "description": "Tender pieces of chicken marinated in a spiced yogurt mixture, grilled to perfection, and served in a creamy tomato-based curry sauce",
        "servingWeight": 400.0,
        "price": 16.99
    }
]
```
### Оновлення страви
URL для перевірки (POST):
   ```bash
http://localhost:8080/api/v1/admin/menu/update
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```

JSON для перевірки:
```bash
{
    "id": 102,
    "name": "Updated Chicken Tikka Masala",
    "description": "Updated Tender pieces of chicken marinated in a spiced yogurt mixture, grilled to perfection, and served in a creamy tomato-based curry sauce",
    "servingWeight": 400.0,
    "price": 16.99
}
```

### Видалення страви з Memu
URL для перевірки (POST):
   ```bash
  http://localhost:8080/api/v1/admin/menu/delete
   ```
Використовуємо токен JWT, який отримали після успішної авторизації.(Bearer Token)

```bash
eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbIlVTRVIiXSwic3ViIjoidXNlcjFAZ2FtaWwuY29tIiwiaWF0IjoxNzAyNDYyNjU4LCJleHAiOjE3MDI0NjQwOTh9.uy5agRP4pK8ZWrpOr2jHZwrfM_t6ks9MJtCSANBRhUo
```
JSON для перевірки:
```bash
{
    "id": 102
}
```

Відповідь:
```bash
[
    {
        "id": 1,
        "name": "Chicken Caesar Salad",
        "description": "Caesar salad with grilled chicken breast",
        "servingWeight": 300.0,
        "price": 11.99
    },
    {
        "id": 52,
        "name": "Margherita Pizza",
        "description": "Traditional Italian pizza with tomatoes and mozzarella",
        "servingWeight": 400.0,
        "price": 14.99
    },
    {
        "id": 53,
        "name": "Grilled Salmon",
        "description": "Salmon fillet seasoned and grilled to perfection",
        "servingWeight": 250.0,
        "price": 17.99
    },
    {
        "id": 54,
        "name": "Vegetarian Stir-Fry",
        "description": "Fresh vegetables stir-fried with tofu in a savory sauce",
        "servingWeight": 220.0,
        "price": 10.99
    },
    {
        "id": 55,
        "name": "Beef Tacos",
        "description": "Soft corn tortillas filled with seasoned beef, lettuce, and cheese",
        "servingWeight": 180.0,
        "price": 8.99
    },
    {
        "id": 56,
        "name": "Caprese Salad",
        "description": "Classic Italian salad with tomatoes, fresh mozzarella, and basil",
        "servingWeight": 150.0,
        "price": 11.49
    },
    {
        "id": 57,
        "name": "Shrimp Pad Thai",
        "description": "Thai stir-fried rice noodles with shrimp, tofu, and peanuts",
        "servingWeight": 300.0,
        "price": 14.99
    }
]
```

