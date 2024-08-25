int main() {
  int a = foo(2) + (foo(2) * (foo(2) / (foo(2) / foo(2)))); // 20
  return 400;
}

int foo(j) {
  return j * j;
}
