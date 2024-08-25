def factorial_iter(x):
    factorial = 1
    for i in range(1, x+1):
        factorial *= i
    return factorial
