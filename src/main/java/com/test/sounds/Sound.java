package com.test.sounds;

import java.util.List;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Back-end representation of an individual sound known to the platform. Each sound belongs to a single Sound Library,
 * and may have multiple files representing different formats/qualities of recording.
 */
@Document (collection = "Sounds")
@Entity("Sounds")
public class Sound {

	/** (Generated) Identifier for the sound. */
	@Id
	private String id;

	/** Name of account that 'owns' the sound. */
	@Indexed
	private String owner;

	/** Identifier of the sound, as established by owner. */
	@Indexed
	private String ownerSoundId;

	/** The name of the sound. */
	private String name;

	/** The file 'implementations' of the sound. */
	private List<SoundFile> files;

	/** Tags associated with the sound. */
	private Set<String> tags;

	/** A transient preview URL for the Sound. */
	@Transient
	private String previewUrl;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the files
	 */
	public List<SoundFile> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(final List<SoundFile> files) {
		this.files = files;
	}

	/**
	 * @return the tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(final String owner) {
		this.owner = owner;
	}

	/**
	 * @return the ownerSoundId
	 */
	public String getOwnerSoundId() {
		return ownerSoundId;
	}

	/**
	 * @param ownerSoundId the ownerSoundId to set
	 */
	public void setOwnerSoundId(final String ownerSoundId) {
		this.ownerSoundId = ownerSoundId;
	}

	/**
	 * @return the previewUrl;
	 */
	public String getPreviewUrl() {
		return previewUrl;
	}

	/**
	 * @param previewUrl the previewUrl to set
	 */
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

}
