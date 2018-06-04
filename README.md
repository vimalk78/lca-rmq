
## Algorithm for the Least Common Ancestor problem.

This implements LCA with "Range Minimum Queries" as described in the paper "The LCA Problmem Revisited".

https://www.ics.uci.edu/~eppstein/261/BenFar-LCA-00.pdf

**Completed** : Naive RMQ, Faster RMQ (using nlogn sparse table)

**TODO** : Implement ±1 RMQ

### Naive RMQ output : 
```
(1(2(4..)(5..))(3(6..)(7..)))
indexs :  0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
values : [1, 2, 4, 2, 5, 2, 1, 3, 6, 3, 7, 3, 1]
levels : [1, 2, 3, 2, 3, 2, 1, 2, 3, 2, 3, 2, 1]
------ query distance --> 
|
|  starting index
V
   0,   0,   0,   0,   0,   0,   6,   6,   6,   6,   6,   6,  12,
   1,   1,   3,   3,   5,   6,   6,   6,   6,   6,   6,  12,   0,
   2,   3,   3,   5,   6,   6,   6,   6,   6,   6,  12,   0,   0,
   3,   3,   5,   6,   6,   6,   6,   6,   6,  12,   0,   0,   0,
   4,   5,   6,   6,   6,   6,   6,   6,  12,   0,   0,   0,   0,
   5,   6,   6,   6,   6,   6,   6,  12,   0,   0,   0,   0,   0,
   6,   6,   6,   6,   6,   6,  12,   0,   0,   0,   0,   0,   0,
   7,   7,   9,   9,  11,  12,   0,   0,   0,   0,   0,   0,   0,
   8,   9,   9,  11,  12,   0,   0,   0,   0,   0,   0,   0,   0,
   9,   9,  11,  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  10,  11,  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  11,  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
------------------------------
LCA of 6 and 7 is 3. Expected : 3
LCA of 4 and 7 is 1. Expected : 1
LCA of 2 and 3 is 1. Expected : 1
LCA of 2 and 2 is 2. Expected : 2
LCA of 1 and 1 is 1. Expected : 1
```
### Faster RMQ (using sparse table) output :
```
(1(2(4..)(5..))(3(6..)(7..)))
indexs :  0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
values : [1, 2, 4, 2, 5, 2, 1, 3, 6, 3, 7, 3, 1]
levels : [1, 2, 3, 2, 3, 2, 1, 2, 3, 2, 3, 2, 1]
------ query distance as power of 2 --> 
|
|  starting index
V
   0,   0,   0,   0,   6,   0,   0,   0,   0,   0,   0,   0,   0,
   1,   1,   1,   3,   6,   0,   0,   0,   0,   0,   0,   0,   0,
   2,   2,   3,   5,   6,   0,   0,   0,   0,   0,   0,   0,   0,
   3,   3,   3,   6,   6,   0,   0,   0,   0,   0,   0,   0,   0,
   4,   4,   5,   6,   6,   0,   0,   0,   0,   0,   0,   0,   0,
   5,   5,   6,   6,   0,   0,   0,   0,   0,   0,   0,   0,   0,
   6,   6,   6,   6,   0,   0,   0,   0,   0,   0,   0,   0,   0,
   7,   7,   7,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,
   8,   8,   9,  11,   0,   0,   0,   0,   0,   0,   0,   0,   0,
   9,   9,   9,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  10,  10,  11,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  11,  11,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
------------------------------
LCA of 6 and 7 is 3. Expected : 3
LCA of 4 and 7 is 1. Expected : 1
LCA of 2 and 3 is 1. Expected : 1
LCA of 2 and 2 is 2. Expected : 2
LCA of 1 and 1 is 1. Expected : 1
```
