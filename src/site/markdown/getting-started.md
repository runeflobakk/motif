# Getting started

Even though Motif has quite an extensive API, there are primarily just two concepts that you need to learn
to benefit from using the library: _map_ and _filter_. Many of the common combinations of `for`-loops and
`if`-statements can be replaced by one type-safe chain of calls to `map` and `filter` methods. Let's go
through some examples to see how _map_ and _filter_ works.


## Get only strings containing a certain substring

We have a list of strings:

```Java
List<String> strings = asList("aa", "abc", "b", "cad", "dddd");
```

Using idiomatic Java, this is how we would pick out all strings that contains "a".

```Java
List<String> result = new ArrayList<String>();
for (String s : strings) {
    if (s.contains("a")) {
        result.add(s);
    }
}
```

With Motif, this can be achieved like this:

```Java
List<String> result = on(strings).filter(contains("a")).collect();
```

Or better yet, if you only need an `Iterable` you can skip the trailing call to `collect()`:

```Java
Iterable<String> result = on(strings).filter(contains("a"));
```

Working with Iterables has the advantage of enabling lazy evaluation, but we will get back to that later.
For now, just have in mind that you can always call `collect()` if you need a Java collection instead of
an `Iterable`. The remaining Motif examples will omit calling `collect()`.

So what is going on in the code? How was the 5 lines of idiomatic Java reduced down to just 1 line of code?

 - The `on(strings)` method, statically imported from [`Iterate`][Iterate], wraps the list of strings in an
   appropriate container, which exposes relevant methods to manipulate the list. 
 - On the returned container, the `filter(contains("a"))` says that only we are only interested in strings
   that contains the "a" substring. No computation is happening here.
 - [`contains("a")`][contains], which is passed to the filter method, is a [`Predicate`][Predicate], statically
   imported from [`Strings`][Strings]. A `Predicate` has only one method accepting an object, in this case a
   _String_, and returns either _true_ or _false_. When retrieveing strings, each of them is passed to the
   method on the predicate, and only when this method returns _true_, the string will be included.
   This computation happens in the `collect()` call.

## What is a predicate?

If you are unit testing using [JUnit](http://junit.org), you are probably familiar with
the `assertThat(something, is(expected))` method where you pass
[Hamcrest matchers](http://code.google.com/p/hamcrest/wiki/Tutorial) which tell if an object is what is
expected. The `assertThat` implementation decides how to react to non-expected objects, i.e. fail the test,
while the Hamcrest matcher does the actual evaluation of the object.

A [`Predicate`][Predicate] is _very_ similar to a Hamcrest matcher, and the separation of concerns between `assertThat(..)`
and a `Matcher` is the same concept as with `filter(..)` and `Predicate`. The Predicate evaulates if objects
are accepted or not, and `filter(..)` decides what should happen when an object is accepted or not. In the case
of _filter_, objects accepted by the predicate is included, while the unaccepted will be omitted.

A `Predicate` is the expression of an `if` statement extracted as a composable unit.



## Get persons whose name is longer than 8 characters

In Java, everything is an object encapsulating other objects. E.g. a very simplified `Person` class
can be defined like this:

```Java
public class Person {
    public final String name;    
    public Person(String name) { this.name = name; }
}
```

And a list of persons can be obtained like this:

```Java
List<Person> persons = asList(new Person("Mary Jane"), new Person("Vicky"), new Person("Billy Bob"));
```

To get persons whose name is longer than 8 characters, we must check the length of each person's name.
Again, with idiomatic Java, this can be achieved with the following code:

```Java
List<Persons> result = new ArrayList<String>();
for (Person p : persons) {
    if (p.name.length > 8) {
        result.add(p.name);
    }
}
```

This looks awfully similar to the previous example picking out strings containing "a". Luckily we have
Motif which lets us express this on one line of code:

```Java
Iterable<Person> result = on(persons).filter(???);
```

Hmm, on a `List<Person>`, of course the `filter` method takes a `Predicate<Person>`. It's easy to write your
own predicate to solve this. Let's add it to `Person`:

```Java
public class Person {
    public final String name;
    public Person(String name) { this.name = name; }

    public static Predicate<Person> nameLengthGreaterThan(final int length) {
        return new Predicate<Person>() {
            @Override public boolean $(Person person) { return person.name.length() > length; }
        };
    }
}
```

Then we are able to neatly express the persons we are interested in:

```
Iterable<Person> result = on(persons).filter(Person.nameLengthGreaterThan(8));
```


## Refactor domain-specific Predicate to domain-specific Fn and generic Predicate

Looking at the predicate we wrote, its implementation is a very basic _greater-than comparison_
of two integers, and it is a pity that we have to write a whole (anonymous) class to achieve
such a simple and rather generic operation. There is really nothing domain-specific about
the evaluation itself, only how one of the operands is obtained with `person.name.length()`. If we
need to compare integer values contained in an other type of object we have to write another almost
identical `Predicate` as well!

Motif provides a number of common general purpose operations as implementations of [the functional
interfaces][functions]. In [`Strings`][Strings] there is the `hasLength` predicate to evaluate
_length of strings_. This can be combined with the _greater-than comparison_ [`greaterThan(8)`][gt] which is
available in [`Base`][Base] like this: `hasLength(greaterThan(8))`.
Also available in `Base` is [`where(Fn, Predicate)`][where], which is used to compose together the two
concerns identified in the original Person-specific predicate we wrote: the domain-specific way to obtain
the value, and the generic way to evaluate the value.

As we now have a way to evaluate `hasLength(greaterThan(8))`, we only need a way to obtain the string
to evaluate. This is a function from `Person` to `String`, or an implementation of
[`Fn<Person, String>`][Fn], since we have persons, but need strings for the evaluation. Let's replace
the `Predicate<Person>` we wrote earlier with a `Fn<Person, String>`:

```Java
public class Person {
    public final String name;
    public Person(String name) { this.name = name; }

    public static final Fn<Person, String> getName = new Fn<Person, String>() {
        @Override public String $(Person person) { return person.name; }
    };
}
```

And using `where` to compose this together:

```Java
Iterable<Person> result = on(persons).filter(where(Person.getName, hasLength(greaterThan(8))));
```







## Get all names from a list of persons


To list out names of the persons, we need strings instead of persons. Again, with idiomatic
Java, this can be achieved with the following code:

```Java
List<String> names = new ArrayList<String>();
for (Person p : persons) {
    names.add(p.name);
}
```

Using Motif, this can be achieved with the _map_ operation like this:

```Java
Iterable<String> result = on(persons).map(Person.getName);
```

Here we see the benefit of the refactoring we did with separating concerns
between domain-specific `Fn` and generic `Predicate`: The domain-specific `Fn`
we wrote to get persons' names is not only applicable for use with filtering, but
for any computation involving Persons and their names.

Note: the _map_ used here has nothing to do with the `Map` data structure of the
Java Collections Framework, but is used as an operation elements. A _map_ operation
is a 1:1 transformation, so doing a _map_ on an amount of elements will yield the
same amount of some other elements. In the case above, the mapping [function][Fn] is
extracting the name from `Person` objects, resulting in a `Person -> String` mapping,
and yielding a `Iterable<String>`.

To further filter the mapped list of persons, we can simply call `filter`:

```Java
Iterable<String> result = on(persons).map(Person.getName).filter(hasLength(greaterThan(8)));
```

The difference between that last example, and filtering with a `where` "clause" as we did
earlier, is that the last example yields a list of strings (the names) instead of persons. So
by providing our own `getName`-function, we can compose this function for various computations to
yield different results based on what we need.




# Good Practice

> __Write domain-specific `Fns` and reuse generic `Predicates`.__

> Favor writing `Predicates` for the actual type it is evaluating,
> and supply how to obtain the value to evaluate with a `Fn`.
 



# More about Motif in general

The following section provides some general notes on the Motif implementation. It is not necessary to read to use Motif,
but it is still recommended to maybe ease further exploring and learning the Motif API.

 

## Bridging Motif with Java Collection Framework

The Java Collections Framework (JCF) is one of the most central APIs of JDK. So for Motif to be any usable, it
needs to work with Java collections as seamless as possible. There are two bridging concepts for moving
back and forth between Java collections and Motif: _on_ and _collect_. You call one of several `Iterate.on(..)` methods
to enable manipulation of any Java collection through the Motif API, and when an Java collection is required, you
call a `collect` method to get the current state of the elements back into a Java collection.

Bridging methods summarized:

 - [`on(..)`][Iterate] Java Collection Framework to Motif
 - [`collect()`][YieldsJavaCollection] Motif to Java Collection Framework




## Motif is lazy

The [`Iterable`][Iterable] interface is
shared between Java collections and Motif, so the call to `collect()` should be deferred as long as possible
until a Java collection is really needed. By making your own code accepting `Iterables` instead of
`Collection/List/Set`, as long as you does not need anything but support for iterating the elements, you will
rarely need to actually call `collect()`. E.g. if a method only uses its passed elements in a `for`-loop,
the method should accept an `Iterable` and not a `Collection/List/Set`.

The laziness of Motif can pretty much be ignored from a behavioral perspective, but may be interesting from a
performance perspective if using large collections. Motif provides an easy way to compose how elements of a
collection are viewed, and ensuring that iterating those elements only happens when necessary, preferably once.
You can even pass around [the iterable obtained from Iterate.on(..)][Elements]
for several participators to adapt, remove and/or add elements, without any iterating taking place until it arriving
to for instance a final `for`-loop, or passed to a 3rd party framework as a Java collection obtained from calling
`.collect()`.

It is worth noting that Motif's main focus is not on performance, but on readability, correctness, and elegance.
The lazyness is merely an implementation detail which is for most practical purposes not really exposed
outside of the API. If manipulating very large collections, high performance, and precise control on lazy
computation are amoung your requirements, Motif may not be suitable for you. For most purposes, Motif will not
have a meassurable degradation on the performance of your code.
 
 
 
 

[Iterable]: http://docs.oracle.com/javase/7/docs/api/index.html?java/lang/Iterable.html "Iterable API (Oracle)"
[Elements]: apidocs/no/motif/types/Elements.html "Elements API"
[Filterable]: apidocs/no/motif/types/Filterable.html "Filterable API"
[Mappable]: apidocs/no/motif/types/Mappable.html "Mappable API"
[Elements]: apidocs/no/motif/types/Elements.html "Elements API"
[YieldsJavaCollection]: apidocs/no/motif/types/YieldsJavaCollection.html "YieldsJavaCollection API"
[Iterate]: apidocs/no/motif/Iterate.html "Iterate class"
[Strings]: apidocs/no/motif/Strings.html "String functions"
[Longs]: apidocs/no/motif/Longs.html "Long functions"
[Ints]: apidocs/no/motif/Ints.html "Integer functions"
[Decimals]: apidocs/no/motif/Decimals.html "Decimal number functions"
[Chars]: apidocs/no/motif/Chars.html "Character functions"
[Base]: apidocs/no/motif/Base.html "Generic base functions"
[Predicate]: apidocs/no/motif/f/Predicate.html "Predicate functional interface"
[Fn]: apidocs/no/motif/f/Fn.html "Fn functional interface"
[contains]: apidocs/no/motif/Strings.html#contains(java.lang.CharSequence) "contains predicate"
[functions]: apidocs/no/motif/f/package-summary.html "Functional interfaces package - no.motif.f"
[gt]: apidocs/no/motif/Base.html#greaterThan(T) "> value Predicate"
[where]: apidocs/no/motif/Base.html#where%28no.motif.f.Fn,%20no.motif.f.Predicate%29 "The where compositional predicate"