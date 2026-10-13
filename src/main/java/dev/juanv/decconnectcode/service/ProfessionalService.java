package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.dto.RegisterRequest;
import dev.juanv.decconnectcode.model.Professional;
import dev.juanv.decconnectcode.repository.ProfessionalRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessionalService(
            ProfessionalRepository professionalRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.professionalRepository = professionalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Professional register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (professionalRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "El correo ya está registrado"
            );
        }

        Professional professional = new Professional();

        professional.setFirstName(
                request.getFirstName().trim()
        );

        professional.setLastName(
                request.getLastName().trim()
        );

        professional.setEmail(email);

        professional.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        return professionalRepository.save(professional);
    }
    public Professional authenticate(String email, String password) {

        Professional professional = professionalRepository
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new IllegalArgumentException("Credenciales incorrectas")
                );

        if (!passwordEncoder.matches(password, professional.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return professional;
    }
}