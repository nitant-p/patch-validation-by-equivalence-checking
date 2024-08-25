int main() {
  int j = 1 + 2 + func(10) * (func(10) / (func(10) - (func(10) + (((func(10)))))));
  return j;
}

int func(int j) {
  return j + j;
}
