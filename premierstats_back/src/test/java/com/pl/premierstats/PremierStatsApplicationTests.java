package com.pl.premierstats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

/**
 * Pending:
 *
 * 1. Add tests for the controller (player and team)
 * 2. Add tests for the service (player and team)
 * 3. Add tests for the repository (player and team)
 * 4. Add tests for the mapper (player)
 * 5. Add tests for the PlayerDTO
 *
 *
 */
@SpringBootTest
@ActiveProfiles("test")
class PremierStatsApplicationTests {

	@Test
	void contextLoads() {
	}

}
