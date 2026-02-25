package main.math;

public final class Bitwise {
	public static int reverseBits(int n) {
		int result = 0;
		for (int i = 0; i < Integer.SIZE; i++) {
			result += (int) (getBit(n, Integer.SIZE - i - 1) * Math.pow(2, i));
		}
		return result;
	}

	public static int getBit(int n, int index) {
		return ((n >> index) & 1);
	}
}
