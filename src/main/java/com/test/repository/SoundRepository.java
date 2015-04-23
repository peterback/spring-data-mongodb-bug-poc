package com.test.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.test.sounds.Sound;

/**
 * Spring {@link MongoRepository} used to interact with back-end datastore containing details of individual sounds.
 */
public interface SoundRepository extends MongoRepository<Sound, String>, QueryDslPredicateExecutor<Sound> {

	/**
	 * Find a given {@link Sound} by its URI path.
	 *
	 * @param owner Owner of sound
	 * @param ownerSoundId Identifier of sound
	 * @return Details of Sound
	 */
	Sound findByOwnerAndOwnerSoundId(String owner, String ownerSoundId);

	/**
	 * Find a list of all managed sound files for a given account.
	 *
	 * @param accountName Name of account to retrieve sound files for
	 * @return Paths to managed files owned by account
	 */
	@Query(value = "{ 'owner' : ?0 }", fields = "{ 'owner' : 1, 'files.fullPath' : 1}")
	List<Sound> findManagedSoundPathsForAccount(String accountName);

	/**
	 * Find sounds based on their account and name.
	 *
	 * @param owner Account to search
	 * @param name Name of sound to search
	 * @param pageable Paging details
	 * @return Collection of {@link Sound}s matching params
	 */
	Page<Sound> findByOwnerAndNameLikeOrderByNameAsc(String owner, String name, Pageable pageable);	
}
