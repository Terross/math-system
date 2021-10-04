# Math-System

## Описание системы

Работа выполнена в качестве учебного проекта. Система используется для
создания и решения различных задач на графы. В условия задачи можно добавлять 
различные внешние модули, которые могут быть загружены в систему.
[Ссылка на сайт](http://math-system.ru). 


Сама система состоит из двух частей:

- **Серверная часть**. Использует **Java Spring** и служит для работы
с базой данных и работе с внешними модулями.
- **Клиентская часть**. Использует **Vue JS** и служит для предоставления
пользователю интерфейса в браузере.

## Написание внешних модулей
Важным свойстом системы является возможность написания дополнительных модулей.
Как это сделать рассказывается  в этой главе. Как пример будет использоваться среда
**IntelliJ IDEA**.

1. Создаем новый maven проект.
   ![Image alt](https://github.com/{Terross}/{math-system}/raw/{main}/images-for-doc/maven-project.png)
   ![alt text](/home/dmitry/Documents/Diplom/math-system/images-for-doc/maven-project.png)
   ![alt text](/home/dmitry/Documents/Diplom/math-system/images-for-doc/project-name.png)


2. В файле **pom.xml** добавляем в зависимости библиотеку для работы с графами. Есть два 
    способа это сделать
   * Загрузить библиотеку в качестве jar файла, где вместо 
     **path-to-jar** следует указать путь к jar файлу **graph-lib.jar**, 
     который можно скачать из этого репозитория.
      ```maven
     <dependencies>
          <dependency>
              <groupId>com.example</groupId>
              <artifactId>math-system</artifactId>
              <version>1.0-SNAPSHOT</version>
              <scope>system</scope>
              <systemPath>path-to-jar</systemPath>
          </dependency>
     </dependencies>
      ```
   * Воспользоваться [jitpack](https://jitpack.io/) (сейчас не работает)
      ```maven
     <repositories>
              <repository>
                  <id>jitpack.io</id>
                  <url>https://jitpack.io</url>
              </repository>
     </repositories>
   
     <dependencies>
        <dependency>
             <groupId>com.github.Terross</groupId>
             <artifactId>math-system</artifactId>
             <version>v1.1</version>
        </dependency>
     </dependencies>
      ```
3. Создаем класс вашей задачи, а затем реализовать необходимый 
    интерфейс
    ```java
   public class GraphSquare implements GraphProperty {
       @Override
       public boolean execute(AbstractGraph abstractGraph) {
           ArrayList<PrimitiveEdge> primitiveEdges = new ArrayList<>();

           for (int j = 0; j < abstractGraph.getVertexCount(); j++) {
               BFS bfs = new BFS(j, abstractGraph);
               bfs.bfs();
               for (int i = 0; i < abstractGraph.getVertexCount(); i++) {
                   if (bfs.pathTo(i) - 1 == 2) {
                       primitiveEdges.add(new PrimitiveEdge(j, i));
                   }
               }
           }

           int grayEdgeCount = 0;
           List<Vertex> vertices = abstractGraph.getVertices();
           List<AbstractEdge> answer = new ArrayList<>();
           for (int i = 0; i < abstractGraph.getVertexCount(); i++) {
            Vertex vertex = vertices.get(i);
               for (AbstractEdge abstractEdge: vertex.getEdgeList()) {
                   if (abstractEdge.getColor() == Color.red) {
                       answer.add(abstractEdge);
                   }
               }
           }

           if (answer.size() != primitiveEdges.size()) {
               return false;
           }

           for (int i = 0; i < answer.size(); i++) {
               int v = answer.get(i).getV().getIndex();
               int w = answer.get(i).getW().getIndex();
               boolean flag = false;
               for (int j = 0; j < answer.size(); j++) {
                   int from = primitiveEdges.get(j).getFrom();
                   int to = primitiveEdges.get(j).getTo();
                   if (((v == from && w == to) || (v == to && w == from))) {
                       flag = true;
                   }
               }
               if (!flag) {
                   return false;
               }
           }
           return true;
       }
   }
   ```
4. Настраиваем проект для создания jar файла модуля
   ![alt text](/home/dmitry/Documents/Diplom/math-system/images-for-doc/createJar.png)
5. Собираем проект и итоговый jar файл готов!

## Загрузка внешних модулей

## Дополнительные возможности


