int main() {
    int i = 10;
    int j = func(3) + func(5); // [Assignment(IntVariable(j), Addition(func(3), func(5)))]
    return j;
}

// {func=[(= $ret k)], main=[(= i 10), (= j (+ (func 3) (func 5))), (= $ret j)]}
// issue is that it doesnt that there are two function calls in one expression

int func(int k) {
    return k;
}
