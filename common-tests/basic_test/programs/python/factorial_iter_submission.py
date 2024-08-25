# Iterative method for factorial
def factorial_iter(n):
    res = 0
    count = 0
    # For loop to increment
    for i in range(1, n+1):
        res *= i
        count += 1
        print(res)
    return res

