package main.math;

public final class VarArgsMath {
	public static double min(double ...numbers) {
		double result = Double.POSITIVE_INFINITY;
		for (double number : numbers) {
			result = Math.min(result, number);
		}
		return result;
	}

	public static double max(double ...numbers) {
		double result = Double.NEGATIVE_INFINITY;
		for (double number : numbers) {
			result = Math.max(result, number);
		}
		return result;
	}

	public static double clamp(double value, double min, double max) {
		return Math.min(max, Math.max(value, min));
	}

	public static double log2(double value) {
		return Math.log(value) / Math.log(2);
	}
}
