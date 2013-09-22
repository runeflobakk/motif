Contributing
========================

Here are a few examples of ways to help out with developing Motif.


Registering issues
-------------------------------
This is probably the easiest way to contribute. If you come up with something you think
is missing, or encounter some bugs, [register it as issues here][issues].




New function implementations
--------------------------------------
There is almost an infinite amount of potential implementations of the functional
interfaces in Motif. The only formal requirement is that they should not have
any other dependencies than the standard JDK classes. Transformations of strings and
other basic value types of the JDK are especially welcome.



New ways to compose functions
--------------------------------------
This is a bit more involved than just creating an implementation of a functional
interface for a specific purpose. As functions can be combined, chained, and other
ways to compose functions into new ones, Motif needs to provide a library of such
composition constructs. Probably the most used ones are the `where`-predicate,
and the various ways to logically combine predicates, all found in the [`Base`][base]
class. Writing new ones
requires a bit of generic thinking, and knowledge about using co- and
contravariant types. These compositions are important, because they are enabling
reuse of functions. If there are a composition you feel is missing in Motif, do
not hesitate to write it and send a pull-request.



Ask questions
-----------------------------
If there is something you are not sure about how you should solve,
or anything else about Motif, just ask! You can post questions on the [issue-tracker][issues].
Any question may also potentially serve as an entry in the [FAQ][faq].




[faq]: faq.html "Frequently asked questions"
[issues]: https://github.com/runeflobakk/motif/issues "Issue tracker on GitHub"
[base]: apidocs/no/motif/Base.html "Base functions"


