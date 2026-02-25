package main.math;

import main.math.vectors.Vector3;

import java.util.Iterator;
import java.util.List;

public final class Mean {
	public static double weightedRootMeanSquare(List<Double> samples, List<Double> weights) {
		if (samples.size() != weights.size()) throw new UnsupportedOperationException("Parameters must have the same size");
		double sumOfWeights = weights.stream().reduce(Double::sum).orElse((double) 0);
		double sumNumerator = 0;
		Iterator<Double> weightIterator = weights.iterator();
		for (double sample : samples) {
			sumNumerator += sample * sample * weightIterator.next();
		}
		return Math.sqrt(sumNumerator / sumOfWeights);
	}

	public static Vector3 weightedRootMeanSquareOfVector3(List<Vector3> samples, List<Double> weights) {
		if (samples.size() != weights.size()) throw new UnsupportedOperationException("Parameters must have the same size");
		double sumOfWeights = weights.stream().reduce(Double::sum).orElse((double) 0);
		Vector3 sumNumerator = Vector3.ZERO;
		Iterator<Double> weightIterator = weights.iterator();
		for (Vector3 sample : samples) {
			sumNumerator = sumNumerator.add(sample.multiply(sample).scale(weightIterator.next()));
		}
		return sumNumerator.scale(1 / sumOfWeights).squareRoot();
	}
}
