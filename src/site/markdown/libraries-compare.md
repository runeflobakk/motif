# Motif compared to other similar libraries


Why do we need yet another "pseudo-functional" library for Java?

Choosing Motif over other similar libraries is mostly a matter of preference.
I wish to emphasize that this should _not_ be read as
"you should use Motif because library X is bad", but rather some important
aspects about a few of the most common similar libraries which differ from Motif,
which can help you make an informed decision as to which library to choose.



## [LambdaJ](http://code.google.com/p/lambdaj/)

This library probably goes the farthest in remedying Java's lack of first class functions,
and quite elegantly manages to implement statically typed method references, and
even [currying](http://en.wikipedia.org/wiki/Currying). The idea behind it is
quite brilliant, and there are few libraries employing the same technique,
[Mockito](http://code.google.com/p/mockito/) being one of them.

LambdaJ uses dynamic proxies to achieve its magic, and it imposes some constraints
and limitations on the code on which it is used. Method references can only be
made on _non-final_ classes and/or _non-final_ methods. For instance, you can not
make any method references on `String`. Such errors are encountered at runtime, not
compile-time.

To "register" and defer a method invocation, LambdaJ needs to store it in a `ThreadLocal`
static variable. It is impossible to compose complex functions which are reuseable,
because making a method reference does not actually yield a function object, but pushes state
to a `ThreadLocal`, though it is managed internally by LambdaJ to give the impression that
real functions are passed. The management of this `ThreadLocal` is very dependent of
the various method calls on the LambdaJ API, and in particular _the order_ of the calls,
and it may in some situations yield some surprises. 

Its implementation is complex, but the library is mature. I have never encountered
any bugs, only the limitations which is inherent of the underlying implementation technique
of LambdaJ.


Motif is based on pure, familiar Java-objects, where no constraints are put on how your
objects are designed and implemented. The "functions" of Motif can be composed
to form new functions, which can be stored and reused throughout your application(s).



##[Guava](http://code.google.com/p/guava-libraries/)

Google's Guava library covers far broader scope of functionality than Motif. Guava is quite common
to be used in projects, and this comparison is based only on its functionality which
overlaps with Motif. 

Guava is similar to Motif in that its "functions" are based on ordinary Java objects.
As Motif, Guava also has a variant of the `Optional` type,
but its capabilities are slightly different from Motif's `Optional`. The `Optional`
type in Motif allows one to adapt when an object should be viewed as "present", the
most common example may be to also treat empty or blank strings as absent. Motif also
has a more unified view the `Optional` type as a special case of a "list-like" container
which may contain a maximum of 1 element, and as such implements the `Iterable`
interface.

The operations in Guava which corresponds to `filter` and `map` are implemented
as static methods, and to achieve lazyness one must reason about which variant of
those operations to use. Motif provides a simpler API
to access the `filter`, `map` and related operations, and all the manipulations
are done as chaining method calls on an `Iterable`. The nature of an `Iterable` makes
laziness occur very naturally. When a Collection implementation is needed, one can
call one of the `collect` methods to get a standard Java Collection back, which
explicitly ends the lazy behavior and computes the resulting elements. Java Collections
are not originally lazy, nor is the API designed for lazyness, and Motif does not try
to fix that; there are no lazy implementations of the `List` interface in Motif.
This principle enables a very simplistic internal implementation in Motif.



##[Functional Java](http://functionaljava.org)

This is a library which aims to bring a very complete functional programming experience
to Java, and in that sense has a bit different philosophy than Motif. As such, it implements
many more of the operations one expect to have available in a functional language. It makes
the library a bit "all-in"; you must make a certain commitment to use it, and use its
data structures throughout your code. Motifs tries to, as transparent as possible, to
amend existing Java collections with its API, as it acknowledges the ubiquity of the Java
Collections Framework.





# Disclaimer

As comparing your own creation to existing solutions is a bit "dangerous", a small
disclaimer seems apropriate. I ackowledge that the comparisons are in no way objective,
and a certain bias is obviously unavoidable.

I apologize in advance if there are any non-balanced or simply inaccurate statements
occuring above, and should you identify any inaccuracies, please don't hesitate to
contact me, so I can do proper adjustements to this document.
