int main() {
    int i = 100;
    int j = func(i); // 100
    int k = func2(j) // 200
    return k;
}

int func(int l) {
    return l * 1;
}

int func2(int m) {
    return m * 2;
}