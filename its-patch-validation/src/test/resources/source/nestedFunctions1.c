int main() {
  int j = 1 + 3 + func(10);
  return j;
}

int func(int j) {
  return j + j;
}
