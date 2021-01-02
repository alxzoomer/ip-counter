# IP Address Counter

## Описание задачи

Дан простой текстовый файл с IPv4 адресами. Одна строка – один адрес, примерно так:

```
145.67.23.4
8.34.5.23
89.54.3.124
89.54.3.124
3.45.71.5
...
```

Файл в размере не ограничен и может занимать десятки и сотни гигабайт.

Необходимо посчитать количество __уникальных адресов__ в этом файле, затратив как можно меньше памяти и времени. Существует "наивный" алгоритм решения данной задачи (читаем строка за строкой, кладем строки в HashSet), желательно чтобы ваша реализация была лучше этого простого, наивного алгоритма.

Немного деталей:
- По всем вопросам смело писать на join@ecwid.com
- Использовать можно только возможности стандартной библиотеки Java/Kotlin
- Писать нужно на Java (версия 11 и выше) или Kotlin.
- В задании должен быть рабочий метод main(), это должно быть готовое приложение, а не просто библиотека
- Сделанное задание необходимо разместить на GitHub

---
Прежде чем отправить задание, имеет смысл проверить его вот на этом [файле](https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip). Внимание – файл весит около 20Gb, а распаковывается приблизительно в 120Gb.

## Предварительные требования

Для сборки и запуска требуется JDK 11+. Источники и варианты установки:

- [Open JDK](https://openjdk.java.net/install/index.html)
- [AdoptOpenJDK](https://adoptopenjdk.net)
- [Oracle JDK](https://www.oracle.com/ru/java/technologies/javase-downloads.html)
- Также можно установить через [brew](https://brew.sh) на MacOS, [Chocolatey](https://chocolatey.org) на Windows
или пакетный менеджер на Linux.

## Получение исходного кода

Получение исходного кода из командной строки:

```shell
git clone https://github.com/alxzoomer/ip-counter.git
cd ip-counter
```

## Сборка приложения

Собрать приложение можно через Gradle из командной строки.

Сборка бинарных файлов:

```shell
# Linux/MacOS
./gradlew build

# Windows
gradlew.bat build
```

Запуск тестов:

```shell
# Linux/MacOS
./gradlew test

# Windows
gradlew.bat test
```

Сборка приложения и упаковка в jar архив:

```shell
# Linux/MacOS
./gradlew jar

# Windows
gradlew.bat jar
```

JAR файл `ip-counter-1.0-SNAPSHOT.jar` соберется в директории `build/libs`.  

## Запуск из командной строки

Запуск через gradelw:

```shell
# Linux/MacOS
./gradlew run --args='/path/to/ip_file'

# Windows
gradlew.bat run --args='c:\path\to\ip_file'
```

Запуск jar файла:

```shell
# Linux/MacOS
java -Xmx1024m -Xms1024m -jar ./build/libs/ip-counter-1.0-SNAPSHOT.jar '/path/to/ip_file'

# Windows
java -Xmx1024m -Xms1024m -jar .\build\libs\ip-counter-1.0-SNAPSHOT.jar 'c:\path\to\ip_file'  
```