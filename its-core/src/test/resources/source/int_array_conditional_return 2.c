int main() {
  int a[5];
  a[0] = 1;
  a[3] = 2;
  if (a[0] == 3) {
    printf("RIP BOZO");
  } else {
    a[4] = 100;
    return a[4];
  }
  return a[0];
}
