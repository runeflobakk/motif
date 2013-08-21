# Getting started

Even though Motif has quite an extensive API, there are primarily just two concepts that you need to learn
to benefit from using the library: _map_ and _filter_. Many of the common combinations of `for`-loops and
`if`-statements can be replaced by one type-safe chain of calls to `map` and `filter` methods. Let's go
through some examples to see how _map_ and _filter_ works.


## Get only strings containing a certain substring

We have a list of strings:

```Java
List<String> strings = asList("aa", "ab", "bb", "ca");
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
