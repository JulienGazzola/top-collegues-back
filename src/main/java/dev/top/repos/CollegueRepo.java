/**
 * 
 */
package dev.top.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.top.entities.Collegue;

/**
 * @author ETY9
 *
 */
public interface CollegueRepo extends JpaRepository<Collegue, Integer> {
	Collegue findByPseudo(String pseudo);
}
