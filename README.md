# mirror

Reflections made easy!

## DEVELOPMENT STATUS

This project is currently in a somewhat stable stage of development. It is not recommended to use
it in production tho, as it is not entirely stable and the API is subject to change at any time (and by
that, I mean it could change completely overnight... I am not kidding, check the commit history. Although, it
is very unlikely to happen at this stage; I mostly just add new API methods).

## What is mirror?

Mirror is a library around java reflections with _a lot_ of useful methods and an extensive
declarative API for working with those. It is dead-easy to get a hang of it; give it a try!

## How do I get started using mirror?

Call the static `fade.mirror.Mirror.mirror` method and provide a class of your choice. If
you have ever worked with java reflections, the rest should be simple auto-completion
assisted fun! :)

### "Ok, but can I get a concrete example, please?"

Let's suppose you have a class and object like this:

```java
class Person {
    String name;
    int age;
    
    ...
    
    void sayHello() {
        System.out.println("Hello, my name is " + name + " and I am " + age + " years old!");
    }
}
```

```java
Person person = new Person("fade", 17);
```

And you want to access the `name` field using reflections. Traditionally, a pretty tedious task!
But using mirror, it's as simple as:

```java
String name = mirror(Person.class)
    .getField(Filter.forFields().ofType(String.class).withName("name"))
    .getValue(person);
```

Et voilà, that's it! You can also set the value of the field, or even invoke methods on the object:

```java
mirror(Person.class)
    .getMethod(Filter.forFields().ofType(String.class).withName("name"))
    .setValue(person, "faden");
```

```java
mirror(Person.class)
    .getMethod(Filter.forMethods().withName("sayHello"))
    .invoke(person);
```

## "I still have questions!"

If you need any help, tutorials or a guide, check out the detailed [javadocs](), the [wiki]()
or the [discord]()!
