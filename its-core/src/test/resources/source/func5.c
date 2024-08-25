int main() {
    int i = 10;
    // int k = 1 + (func(i) * 2);
    int j = func(10); //throw return value from func into here
    return j;
}

int func(int i) {
    return i + i + i + i + i + i + i + i + i + i;
}
// create separate solver for the callee and get return value