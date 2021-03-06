Documentation
=====================

## Articles

- [Getting started](getting-started.html)
- [The `Optional` type](optional.html)
- [Motif compared to other similar libraries](libraries-compare.html)
- [How to contribute](contributing.html)

- [Frequently asked questions](faq.html)



## The API

- [`Iterate`][Iterate]: The entry point for working with _collections_ with Motif. Home of
  several overloaded static `on(..)` methods, which accepts common Java objects, and enables
  manipulating it through Motif's [`Elements`][Elements] API.
- [`Singular`][Singular]: The entry point for working with _single values_ with Motif. Here you
  create instances of [`Optional`](optional.html), which is a special construct for dealing with
  single values/objects without ever needing to do tedious `null`-checking.
  

### Functional interfaces

The functional interfaces of the [`no.motif.f`][functions] package are single method
interfaces that imitate function types. The two most important ones are:

- [`Fn<I, O>`][Fn]: functional (single-method) interface where implementing methods are passed objects
  of one type (I), and returns objects of the same or another type (O).
- [`Predicate<T>`][Predicate]: functional interface where implementing methods evaluates
  objects of type T as either `true` or `false`. A _predicate_ is really
  a specialization of `Fn<I,O>` where the return type (O) is `boolean`, but since a _predicate_
  is such a central concept, it has got its own interface, and since Java does not allow
  type parameterization with primitive types, this avoids the possibility of returning `null`
  from predicates.


### Function implementations

- [`Base`][Base]: generic operations, as well as providing various ways to compose operations
  to form new operations.
- [`Strings`][Strings]: useful implementations of `Predicate`, `Fn`, and other functional
  interfaces for working with strings
- [`Ints`][Ints]: operations for working with `int` values.
- [`Longs`][Longs]: operations for working with `long` values.
- [`Chars`][Chars]: operations for working with `char` values.
- [`Decimals`][Decimals]: operations for working with decimal number (`double`) values.


### API documentation

- [Javadocs](apidocs/index.html)






[Iterable]: http://docs.oracle.com/javase/7/docs/api/index.html?java/lang/Iterable.html "Iterable API (Oracle)"
[Elements]: apidocs/no/motif/types/Elements.html "Elements API"
[Filterable]: apidocs/no/motif/types/Filterable.html "Filterable API"
[Mappable]: apidocs/no/motif/types/Mappable.html "Mappable API"
[Iterate]: apidocs/no/motif/Iterate.html "Iterate class"
[Elements]: apidocs/no/motif/types/Elements.html "Elements API" 
[Singular]: apidocs/no/motif/Singular.html "Singular class"
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
