/**
 * 
 */
package dev.top.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import dev.top.entities.Action;
import dev.top.entities.Avis;
import dev.top.entities.Collegue;
import dev.top.entities.CollegueApi;
import dev.top.repos.CollegueRepo;

/**
 * @author ETY9
 *
 */
@RestController()
@CrossOrigin
@RequestMapping("/collegues")
public class CollegueCtrl {

	@Autowired
	private CollegueRepo collegueRepo;

	@GetMapping
	public List<Collegue> findAll() {
		return this.collegueRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/{pseudo}")
	public Collegue Edit(@PathVariable String pseudo, @RequestBody Action act) {
		Collegue currentCollegue = this.collegueRepo.findByPseudo(pseudo);

		if (act.getAction().equals(Avis.AIMER)) {
			currentCollegue.setScore(currentCollegue.getScore().add(BigDecimal.valueOf(10)));
		} else {
			currentCollegue.setScore(currentCollegue.getScore().subtract(BigDecimal.valueOf(5)));
		}
		collegueRepo.save(currentCollegue);
		return currentCollegue;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{pseudo}")
	public Collegue Detail(@PathVariable String pseudo) {
		Collegue currentCollegue = this.collegueRepo.findByPseudo(pseudo);
		if (currentCollegue == null) {

		}
		return currentCollegue;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/nouveau")
	public Collegue Nouveau(@RequestBody Collegue collegue) {
		Collegue newCollegue = new Collegue();

		CollegueApi[] collegues = new RestTemplate().getForObject(
				"http://collegues-api.cleverapps.io/collegues?matricule=" + collegue.getMatricule(),
				CollegueApi[].class);

		if (collegue.getMatricule() != null && collegue.getPseudo() != null && !collegue.getMatricule().isEmpty()
				&& collegue.getMatricule() != null && collegue.getPseudo().length() >= 2) {
			newCollegue.setPseudo(collegue.getPseudo());
			newCollegue.setMatricule(collegue.getMatricule());
			newCollegue.setScore(BigDecimal.valueOf(0));

			if (collegue.getImageUrl() == null || collegue.getImageUrl().isEmpty()) {
				newCollegue.setImageUrl(
						"http://pluspng.com/img-png/cute-baby-bird-png-cute-baby-clipart-cliparts-co-405.png");
			} else {
				newCollegue.setImageUrl(collegue.getImageUrl());
			}
			collegueRepo.save(newCollegue);
		}
		return newCollegue;
	}

}
