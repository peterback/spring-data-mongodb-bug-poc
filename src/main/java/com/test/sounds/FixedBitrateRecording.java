package com.test.sounds;

/**
 * Representation of a recording that uses a fixed bitrate.
 */
public class FixedBitrateRecording extends RecordingQuality {

	/**
	 * @param fixedBitrate Bitrate recording uses (in kbps)
	 */
	public FixedBitrateRecording(final int fixedBitrate) {
		super(false, false, fixedBitrate);
	}

}
