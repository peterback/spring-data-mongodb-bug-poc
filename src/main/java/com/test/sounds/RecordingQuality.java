package com.test.sounds;

/**
 * Measure of the quality of a given sound recording.
 */
public abstract class RecordingQuality implements Comparable<RecordingQuality> {

	/** Whether the file uses lossless compression. */
	private final boolean losslessCompression;

	/** Whether the file uses VBR compression. */
	private final boolean variableBitrate;

	/** The (fixed) bitrate the recording uses. <code>null</code> if variable or lossless. */
	private final Integer fixedBitrate;

	/**
	 * @param losslessCompression Whether the file uses lossless compression
	 * @param variableBitrate Whether the file uses VBR compression
	 * @param fixedBitrate The (fixed) bitrate the recording uses
	 */
	protected RecordingQuality(
			final boolean losslessCompression, final boolean variableBitrate, final Integer fixedBitrate) {
		this.losslessCompression = losslessCompression;
		this.variableBitrate = variableBitrate;
		this.fixedBitrate = fixedBitrate;
	}

	@Override
	public int compareTo(final RecordingQuality otherRecordingQuality) {
		if (this.isLosslessCompression()) {
			if (otherRecordingQuality.isLosslessCompression()) {
				return 0;
			} else {
				return 1;
			}
		} else if (this.isVariableBitrate()) {
			if (otherRecordingQuality.isLosslessCompression()) {
				return -1;
			} else if (otherRecordingQuality.isVariableBitrate()) {
				return 0;
			} else {
				return 1;
			}
		} else {
			if (otherRecordingQuality.isLosslessCompression()) {
				return -1;
			} else if (otherRecordingQuality.isVariableBitrate()) {
				return -1;
			} else {
				return this.fixedBitrate - otherRecordingQuality.getFixedBitrate();
			}
		}
	}

	/**
	 * @return the losslessCompression
	 */
	public boolean isLosslessCompression() {
		return losslessCompression;
	}

	/**
	 * @return the variableBitrate
	 */
	public boolean isVariableBitrate() {
		return variableBitrate;
	}

	/**
	 * @return the fixedBitrate
	 */
	public Integer getFixedBitrate() {
		return fixedBitrate;
	}

}
