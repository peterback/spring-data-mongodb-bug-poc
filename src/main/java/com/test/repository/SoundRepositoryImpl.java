package com.test.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.test.sounds.Sound;
import com.test.sounds.SoundFile;

/**
 * Custom queries to add to the Spring Data Repository relating to {@link Sound}s.
 */
public class SoundRepositoryImpl implements SoundRepositoryCustom {

	/** {@link MongoOperations} object, used to build and execute custom queries. */
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public boolean existingSoundWithOwnerIdentifier(final String owner, final String ownerSoundId) {
		return mongoOperations.exists(
				query(where("owner").is(owner).and("ownerSoundId").is(ownerSoundId)), Sound.class);
	}

	@Override
	public void addFilesToExistingSound(final String owner, final String ownerSoundId, final SoundFile... soundFiles) {
		final Query query = query(where("owner").is(owner).and("ownerSoundId").is(ownerSoundId));
		final Update update = new Update();

		for (final SoundFile soundFile : soundFiles) {
			update.addToSet("files", soundFile);
		}

		mongoOperations.findAndModify(query, update, Sound.class);
	}

	@Override
	public Map<String, Integer> managedSoundsCountByAccount() {
		final Aggregation aggregation =
				newAggregation(
						group("owner").count().as("soundCount"),
						project("soundCount").and("owner").previousOperation(),
						sort(Direction.DESC, "soundCount"));

		final AggregationResults<SoundsByAccount> aggregationResults =
				mongoOperations.aggregate(aggregation, Sound.class, SoundsByAccount.class);

		final Map<String, Integer> counts = new TreeMap<>();
		for (final SoundsByAccount result : aggregationResults.getMappedResults()) {
			counts.put(result.getOwner(), result.getSoundCount());
		}

		return counts;
	}

	/**
	 * Encapsulation of number of sounds by account.
	 */
	private class SoundsByAccount {

		/** Platform account name. */
		private String owner;

		/** Number of sounds. */
		private int soundCount;

		/**
		 * @return the owner
		 */
		public String getOwner() {
			return owner;
		}

		/**
		 * @param owner the owner to set
		 */
		@SuppressWarnings("unused")
		public void setOwner(final String owner) {
			this.owner = owner;
		}

		/**
		 * @return the soundCount
		 */
		public int getSoundCount() {
			return soundCount;
		}

		/**
		 * @param soundCount the soundCount to set
		 */
		@SuppressWarnings("unused")
		public void setSoundCount(final int soundCount) {
			this.soundCount = soundCount;
		}
	}
}
