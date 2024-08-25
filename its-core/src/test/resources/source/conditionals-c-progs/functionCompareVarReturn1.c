int main() {
    int i = 1;
    if (func(i) == 10) {
        return 1;
    } else {
        return 0;
    }
}

int func(int i) {
    return i + i + i + i + i + i + i + i + i + i;
}
