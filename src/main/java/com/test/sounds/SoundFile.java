package com.test.sounds;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Representation of a file 'implementation' of a sound.
 */
public class SoundFile {

	/** The full path to the file within the library's storage service. */
	@Indexed
	private String fullPath;

	/** File type, as represented by the file extension. */
	private String fileType;

	/** The quality of the sound recording. */
	private RecordingQuality recordingQuality;

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(final String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(final String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the recordingQuality
	 */
	public RecordingQuality getRecordingQuality() {
		return recordingQuality;
	}

	/**
	 * @param recordingQuality the recordingQuality to set
	 */
	public void setRecordingQuality(final RecordingQuality recordingQuality) {
		this.recordingQuality = recordingQuality;
	}

}
