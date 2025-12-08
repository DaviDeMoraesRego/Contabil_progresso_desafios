package br.com.contabil.progresso.desafios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.contabil.progresso.desafios.entity.ContabilProgressoDesafiosEntity;

@Repository
public interface ContabilProgressoDesafiosRepository extends JpaRepository<ContabilProgressoDesafiosEntity, Integer> {

	@Query(value = """
			SELECT
			    CASE
			        WHEN COUNT(DISTINCT tab_desafios.id) =
			             SUM(CASE WHEN tab_progresso_desafios.completo = 1 THEN 1 ELSE 0 END)
			        THEN 1
			        ELSE 0
			    END
			FROM tab_desafios
			LEFT JOIN tab_progresso_desafios
			    ON tab_desafios.id = tab_progresso_desafios.desafios_id
			   AND tab_progresso_desafios.clerk_id = :clerkId
			WHERE tab_desafios.licoes_id = :licaoId
			""", nativeQuery = true)
	int isLicaoCompleta(@Param("clerkId") String clerkId, @Param("licaoId") int licaoId);

	@Query(value = "SELECT * FROM tab_progresso_desafios WHERE clerk_id = :clerkId AND desafios_id = :desafioId", nativeQuery = true)
	Optional<ContabilProgressoDesafiosEntity> findByUsuarioAndDesafioId(@Param("clerkId") String clerkId,
			@Param("desafioId") int desafioId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE tab_progresso_desafios SET completo = true WHERE clerk_id = :clerkId AND desafios_id = :desafioId", nativeQuery = true)
	void updateCompletoStatus(@Param("clerkId") String clerkId, @Param("desafioId") int desafioId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM tab_progresso_desafios WHERE desafios_id = :desafioId AND clerk_id = :clerkId", nativeQuery = true)
	void resetLicao(@Param("clerkId") String clerkId, @Param("desafioId") int desafioId);
}
