Optional&lt;V&gt;
============================

[`Optional`][Optional-type], or variants of it, is a ubiquitous type typically
present in functional or hybrid-functional
languages. It is used for cases when it is valid for a procedure (function, method, etc) to terminate
with no result. In Java, the idiomatic way to do this is to return the dreaded null-pointer, or
`return null`. However, the possibility of a "no result"-result, or `null`, cannot be expressed by
the type system, and is typically the source of numerous `NullPointerExceptions` where the caller of a
method forgets to check if the returned value is, in fact, an instance before dereferencing it.

The Optional type explicitly expresses that a value may be something, or may be nothing, at the type level.


Basic use
-----------------------
An Optional can be constructed using the
[`no.motif.Singular.optional(..)`][optional]
factory method. It should be statically imported and used like this:

```java
String string = //get string or null from somewhere
Optional<String> optString = optional(string);
```

Now, `optString` is either an instance of `Some<String>` or the singleton `None`. It can be queried
using [`optString.isSome()`][isSome]
to determine if the string is indeed present or not, and if it is, it can
be obtained with [`optString.get()`][get].

The procedure described above looks more like a glorified old-fashioned null check, and not that
much of any added value. The `!= null` has been replaced with a call to `isSome()`, and using the
value requires a tedious call to `get()`. If anything, we have managed to somewhat force to check
for the presence of the value, which can be argued to be a slight improvement, but it isn't in any
way spectacular.


Iterating an Optional
---------------------------
An Optional can be viewed as an `Iterable` which is either empty or has exactly one element
(analogous to a list with either zero or one element). In fact, `Optional` implements the
`Iterable` interface, which makes it applicable for use with a standard `for`-loop in Java.
Now, it may seem strange to "iterate" one value, but what we are actually using the loop for
is to say "if present, then assign and execute block".

Using `optString` from the previous example, we can print the string if it exists:

```java
for (String s : optString) System.out.println(s);
```

We can even write it on one line, and it doesn't look half-bad.





More operations on Optional&lt;V&gt;
----------------------------------------
The Optional type supports the use of the various function interfaces in Motif to enable
safe operations on the optional value.


### Deciding `Some` or `None` based on predicate

By default, the `optional(..)` factory method treats `null` as nothing, and any instance
as something. But it may be situations where you want to adapt how values are viewed.
For instance, you may want to treat both null and empty strings as nothing. This can be
achieved by using
[`optional(Predicate, V)`][optional-with-predicate],
where the predicate decides if the value should be treated as "present".

In the Strings class there is a predicate for precisely this purpose: 
[`blank`](apidocs/no/motif/Strings.html#blank).
We can negate the predicate with [`not(blank)`](apidocs/no/motif/Base.html#not)
and use it with `optional` like this:

```java
for(String s : optional(not(blank), "")) throw new RuntimeException();
```

No exception will be thrown in the above code. If we did not include the predicate,
`String s` would be assigned the empty string, and a `RuntimeException` would have
been thrown.




### Map the optional value

`Optional` also supports mapping (or transforming) its value to something else. The
mapping operation is safe; you can perfectly map a non-existing value. The key is
that the [`map(..)`][map]
method returns a new Optional of the result type of the mapping
function (or `None` if the mapper function maps to `null` or any other undefined value
as decided by an optional predicate). The mapping functions are implementations of the
[`Fn<I, O>`](apidocs/no/motif/f/Fn.html) interface.

Say we have a string that may or may not be numeric and we are only interested in it
if indeed it is numeric. We need a predicate to decide if a string is numeric:

```java
public final Predicate<String> numeric = new Predicate<String>() {
    public boolean $(String string) {
        return !StringUtils.isNumeric(string);
    }
};
```


We also need to parse the string as an `int` to be able to use it, so we define a transformer for that:

```java
public final Fn<String, Integer> toInt = new Fn<String, Integer>() {
    public Integer $(String string) {
        return Integer.parseInt(string);
    }
};
```

The two functions defined above can then be used with `optional` like this:

```java
String mayBeNumber = //get the number
for (int i : optional(numeric, mayBeNumber).map(toInt)) {
    // do something with i
}
```

If `mayBeNumber` is numeric, the string is converted to an integer and assigned
to `int i`, and the block is executed. If mayBeNumber is not numeric, the `None`
singleton instance is returned from both `optional(..)`, and `map(..)`, and
following no assignment is done to `i`.




[Optional-type]: <apidocs/no/motif/single/Optional.html> "The Optional type"
[optional]: <apidocs/no/motif/Singular.html#optional%28V%29> "Factory method for Optional"
[optional-with-predicate]: <apidocs/no/motif/Singular.html#optional%28no.motif.f.Predicate,%20V%29> "Factory method for Optional with Predicate"
[none]: apidocs/no/motif/Singular.html#none%28%29 "The None instance"
[isSome]: apidocs/no/motif/single/Optional.html#isSome%28%29 "Optional.isSome() method"
[get]: apidocs/no/motif/single/Optional.html#get%28%29 "Optional.get() method"
[map]: apidocs/no/motif/single/Optional.html#map%28no.motif.f.Fn%29 "Optional.map(..) method"
