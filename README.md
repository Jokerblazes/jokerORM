# jokerORM

`一个全自动的ORM框架。`

## 优势

* 自动生成sql
* 自动将ResultSet注入实体中
* 自定义过滤器（可自定义分页器、缓存器）

## maven

```xml
<repositories>
	<repository>
		<id>Jokerblazes-jokerORM</id>
		<url>https://raw.github.com/Jokerblazes/maven-project/master/com/joker/jokerORM</url>
	</repository>
	<repository>
		<id>Jokerblazes-tx</id>
		<url>https://raw.github.com/Jokerblazes/maven-project/master/com/joker/tx</url>
	</repository>
</repositories>

<dependency>
      <artifactId>jokerORM</artifactId>
      <groupId>com.joker</groupId>
      <version>0.0.1-SNAPSHOT</version>
 </dependency>
```

## Demo

```java
SqlHelper helper =  (SqlHelper) SqlProxyFactory.getInstance().getSession();
List<User> users = helper.selectList(new User());
```

