package com.test.repository;

import java.util.Map;

import com.test.sounds.SoundFile;

/**
 * Custom additions to the Spring Data Repository representing sounds.
 */
public interface SoundRepositoryCustom {

	/**
	 * Determine whether the Platform has a sound belonging to the specified owner, with the given identifier.
	 *
	 * @param owner Owner of the sound
	 * @param ownerSoundId Identifier of the sound
	 * @return Whether we have a sound with the path
	 */
	boolean existingSoundWithOwnerIdentifier(String owner, String ownerSoundId);

	/**
	 * Atomically add details of new files to an existing Sound.
	 *
	 * @param owner Owner of the sound
	 * @param ownerSoundId Identifier of the sound
	 * @param soundFiles {@link SoundFile}s to add
	 */
	void addFilesToExistingSound(String owner, String ownerSoundId, SoundFile... soundFiles);

	/**
	 * Determine the number of sounds belonging to accounts that are managed by the Platform.
	 *
	 * @return Number of sounds by account
	 */
	Map<String, Integer> managedSoundsCountByAccount();
	
}
