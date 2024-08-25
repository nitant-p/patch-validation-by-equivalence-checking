int main() {
    int i = 10;
    int j = func(i);
    return j;
}

int func(int i) {
    return func2(i);
}

int func2(int i) {
    return i + 3019;
}
