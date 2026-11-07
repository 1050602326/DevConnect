
package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.JobOffer;
import dev.juanv.decconnectcode.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findByCompany(Company company);

    List<JobOffer> findByCompanyAndActiveTrue(Company company);

    List<JobOffer> findByActiveTrue();
}
