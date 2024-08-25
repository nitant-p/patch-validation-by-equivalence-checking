int main() {
    int i = 10;
    int j = func(i);
    return j;
}

int func(int i) {
    int k = i
    return func2(k);
}

int func2(int m) {
    return m + 3019;
}
