# SpringBoot 整合 Thymeleaf

Thymeleaf 是一个跟 Velocity、FreeMarker 类似的模板引擎，它可以完全替代 JSP 。也就是说JSP中的特性在Thymeleaf几乎都有对应的支持。Thymeleaf支持HTML原型，通过Thymeleaf特殊的标签可以基本实现JSP中动态数据的展示效果。


## 配置

Thymeleaf依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

application.yml

```yaml
spring:
  thymeleaf:
    #开发时关闭缓存,不然没法看到实时页面
    cache: false
    mode: LEGACYHTML5
    encoding: UTF-8
    servlet:
      content-type: text/html
    suffix: .html
```
其中`spring.thymeleaf.mode = LEGACYHTML5`配置thymeleaf的模式，不要使用`spring.thymeleaf.mode = HTML5`，因为严格遵循HTML5规范会对非严格的报错，标签没有闭合就会报错。


Thymeleaf 支持渲染HTML，因此通常我们使用的页面模板也就是HTML，同时它需要遵循一定的规则：

1. 比如在spring boot项目中，通常我们将Thymeleaf渲染的页面放在`resources/templates`目录下，这样Thymeleaf会默认识别。

2. 若想要在HTML页面中使用Thymeleaf，需要修改`<html lang="en">`为`<html lang="en" xmlns:th="http://www.thymeleaf.org">`

3. 在spring boot项目中`resources/static`目录下的文件可通过浏览器直接访问，而`resources/templates`下的HTML不能通过浏览器直接访问。


## URL表达式

```html
<script th:src="@{/css/index.css}"></script>
```

会自动引入`resources/statis/css/`下的`index.css`文件


## 表达式支持的语法

**字面**

- 文本文字: 'one text', 'Another one!',…
- 数字文本）: 0, 34, 3.0, 12.3,…
- 布尔文本: true, false
- 空: null
- 文字标记: one, sometext, main,…

**文本操作**

- 字符串连接: +
- 文本替换: |The name is ${name}|

**算术运算**

- 二元运算符: +, -, *, /, %
- 减号（单目运算符）: -

**布尔操作**

- 二元运算符:and, or
- 布尔否定（一元运算符）:!, not

**比较和等价**

- 比较: >, <, >=, <= (gt, lt, ge, le)
- 等值运算符:==, != (eq, ne)

**条件运算符**
- If-then: (if) ? (then)
- If-then-else: (if) ? (then) : (else)
- Default: (value) ?: (defaultvalue)

留一个Star鸭 蟹蟹🎉 🎉 🎉 
