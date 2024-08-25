int main() {
    int i = 10;
    int j = func(i);
    int k = func2(i);
    return j + k;
}

int func(int i) {
    i = func2(i);
    return i;
}

int func2(int i) {
    return i + 3019;
}
