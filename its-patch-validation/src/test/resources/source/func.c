int main() {
    int i = 10; 
    // int k = 1 + (func(i) * 2);
    int j = func(10); // TODO: HAS VALUE AND VARIABLE AS ARGUMENT 
    return j;
}

int func(int i) {
    return i * i;
}