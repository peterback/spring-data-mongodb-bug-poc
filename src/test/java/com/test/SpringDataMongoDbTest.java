package com.test;

import static org.junit.Assert.fail;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import com.test.repository.SoundRepository;
import com.test.sounds.FixedBitrateRecording;
import com.test.sounds.Sound;
import com.test.sounds.SoundFile;

public class SpringDataMongoDbTest {
	
	@Test
	public void testPersistSound() {
		try {
			MongoTemplate mongoOperations = new MongoTemplate(mongo(), "platform_test");
			RepositoryFactorySupport factory = new MongoRepositoryFactory(mongoOperations);
			SoundRepository soundRepository = factory.getRepository(SoundRepository.class);

			persistSound("testOwnerId", "/full/path/to/sound1");
			persistSound("testOwnerId", "/full/path/to/sound2");
			try {
				Sound loaded = soundRepository.findByOwnerAndOwnerSoundId("testAccount", "testOwnerId");
				soundRepository.delete(loaded);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to deserialise Sound!");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void persistSound(String ownerSoundId, String fullPath) {
		try {
			MongoTemplate mongoOperations = new MongoTemplate(mongo(), "platform_test");
			RepositoryFactorySupport factory = new MongoRepositoryFactory(mongoOperations);
			SoundRepository soundRepository = factory.getRepository(SoundRepository.class);
			
			String accountName = "testAccount";
			final List<SoundFile> newSoundFiles = new ArrayList<>();
			SoundFile soundFile1 = new SoundFile();
			soundFile1.setFileType("mp3");
			soundFile1.setFullPath(fullPath);
			soundFile1.setRecordingQuality(new FixedBitrateRecording(128));
			newSoundFiles.add(soundFile1);
			
			if (mongoOperations.exists(
					query(where("owner").is(accountName).and("ownerSoundId").is(ownerSoundId)), Sound.class)) {

				// Add files to existing sound
				final Query query = query(where("owner").is(accountName).and("ownerSoundId").is(ownerSoundId));
				final Update update = new Update();

				for (final SoundFile soundFile : newSoundFiles) {
					update.addToSet("files", soundFile);
				}

				mongoOperations.findAndModify(query, update, Sound.class);
			} else {
				// Create sound an add files
				final String soundName = "testSoundName";
				final Set<String> tags = new HashSet<String>();
				tags.add("testTag1");
				tags.add("testTag2");

				final Sound sound = new Sound();
				sound.setOwner(accountName);
				sound.setOwnerSoundId(ownerSoundId);
				sound.setName(soundName);
				sound.setTags(tags);
				sound.setFiles(newSoundFiles);

				soundRepository.save(sound);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Mongo mongo() throws Exception {
		final MongoURI mongoURI = new MongoURI ("mongodb://localhost:27017/platform_test");
		DB db = mongoURI.connectDB();
		return db.getMongo();
	}
}
