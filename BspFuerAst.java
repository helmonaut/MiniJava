class A {
public int x;
public int y;

public int calc(int x, int y) {
	int sum;

	if (x > y) {
		sum = x + y;
	} else {
		sum = x * y;
	}

	return sum;
	}
}