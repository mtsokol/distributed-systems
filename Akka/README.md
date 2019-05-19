# Akka

Library system for searching, ordering and streaming books implemented with `Akka Typed Actors`

## Usage

Run `Client` and `Server` in separate sbt servers.  

Example usage within client:

```
> o book1
OrderCompleted

> s zbrodnia_i_kara
Book(BookTitle(zbrodnia_i_kara),Price(26.59))

> c baczynski.txt
Noc zielona była, po dniu skwarnym
głębokość jej szumiała jakby liście czarne,
w których mleczny rdzeń wyrósł, i kroplami gwiazd
odmierzał się powoli nieostrożny czas. 
[...]
StreamCompleted

```
