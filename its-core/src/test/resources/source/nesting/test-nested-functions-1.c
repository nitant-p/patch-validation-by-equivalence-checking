int main() {
  int a = foo(2) + (foo(2) * (foo(2) / (foo(2) / foo(2)))); // 20
  return (((foo(a))));
}

int foo(j) {
  return j * j;
}
