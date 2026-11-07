
package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.model.Company;
import dev.juanv.decconnectcode.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(
            CompanyRepository companyRepository,
            PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Company registerCompany(Company company) {

        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new IllegalArgumentException(
                    "El correo electrónico ya está registrado");
        }

        company.setPassword(
                passwordEncoder.encode(company.getPassword()));

        return companyRepository.save(company);
    }

    public Optional<Company> findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    public boolean emailExists(String email) {
        return companyRepository.existsByEmail(email);
    }

    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company authenticate(String email, String password) {

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Correo o contraseña incorrectos"));

        if (!passwordEncoder.matches(password, company.getPassword())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }

        return company;
    }

    public Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Empresa no encontrada"));
    }
}
