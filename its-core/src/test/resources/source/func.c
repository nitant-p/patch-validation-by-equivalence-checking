int main() {
    int i = 10; 
    // int k = 1 + (func(i) * 2);
    int j = func(10); // TODO: HAS VALUE AND VARIABLE AS ARGUMENT  // func, 10 ; func, "hi"
    // k, 10; l, "hi"
    return j;
}

int func(int k) {
    return k * k; // i * i
}