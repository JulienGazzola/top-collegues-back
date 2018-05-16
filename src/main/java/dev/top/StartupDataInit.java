package dev.top;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import dev.top.entities.Collegue;
import dev.top.entities.Version;
import dev.top.repos.CollegueRepo;
import dev.top.repos.VersionRepo;

@Component
public class StartupDataInit {

	@Autowired
	VersionRepo versionRepo;
	@Autowired
	CollegueRepo collegueRepo;

	@EventListener(ContextRefreshedEvent.class)
	public void init() {

		if (this.versionRepo.count() <= 0) {
			this.versionRepo.save(new Version("v1"));
			this.versionRepo.save(new Version("v2"));
			this.versionRepo.save(new Version("v3"));
			this.versionRepo.save(new Version("v4"));
		}

		if (this.collegueRepo.count() <= 0) {
			this.collegueRepo
					.save(new Collegue("Jeannot", BigDecimal.valueOf(-1), "Jean", "Luc", "12 rue toto Totocity",
							"http://pluspng.com/img-png/cute-baby-bird-png-cute-baby-clipart-cliparts-co-405.png"));
			this.collegueRepo
					.save(new Collegue("Jeannette", BigDecimal.valueOf(1), "Jeanne", "tte", "15 rue tata Tatacity",
							"http://pluspng.com/img-png/cute-baby-bird-png-cute-baby-clipart-cliparts-co-405.png"));
		}
	}
}
