# Akka

Library system for searching, ordering and streaming books implemented with `Akka Typed Actors`

## Usage

Run `Client` and `Server` in separate sbt servers.  

Example usage within client:

```
> o book1
OrderCompleted

> s pan_tadeusz
Book(BookTitle(pan_tadeusz),Price(23.23)

> c book2
number
five
StreamCompleted

```
