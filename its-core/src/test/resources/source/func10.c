int main() {
    int i = 100;
    int j = func2(i); // 200
    int k = func(j) // 200
    return k;
}

int func(int l) {
    return l * 1;
}

int func2(int m) {
    return m * 2;
}