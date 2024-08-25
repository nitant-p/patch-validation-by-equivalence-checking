int main() {
    int i = 10;
    if (func(i)) {
        return 1;
    } else {
        return 0;
    }
}

int func(int i) {
    return i + i + i + i + i + i + i + i + i + i;
}
