package com.eci.ARSW.TalleresDEP.TallerDEP;

import com.eci.ARSW.TalleresDEP.TallerDEP.relational.RelationalDataAccessApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RelationalDataAccessApplication.class)
@ImportAutoConfiguration(exclude = {
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class
})
class RelationalDataAccessApplicationTest {
	@Test
	void contextLoads() {
	}
}

