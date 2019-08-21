# Distribute amount between slots
Algorithm to distribute some amount between several slots with some limitations.

Model example is reward distribution in some games.
Say you want to give `n` units of rewards with exactly `k` (`k < n`) different types of reward.

Following limitations are considered:
1. Each type of reward be given (so number in each slot must be positive).
2. Average value in each slot must be `n / k` (for many enough runs).
3. You may want distribution to be more or less centered around average result.
This is regulated by an optional parameter `rollsNum`: minimum value of `1` gives least centered result, the more it get, the more centered results you will get.
Recommended values are `1`, `2` and `3`. 
4. Each allowed combination should be theoretically achievable.

Method used is invented empirically basing on these requirements. 

For example, trying to distribute `100` units between `5` slots with minimal `rollsNum = 1` you can get some of these variants
```
18  25   1  18  38
 4  24   3  59  10
 4  33  36   1  26
22  10   8  38  22
 3   9  23  29  36
22  27  23  12  16
35  28  10  13  14
39   6  23  26   6
32  24   3  37   4
34   5  18  18  25
28  29   5  24  14
22   5  44   8  21
18   9  15  32  26
38  15   2  24  21
16   9  15  18  42
35  16  11   6  32
``` 
