package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.dto.ProfileRequest;
import dev.juanv.decconnectcode.model.Professional;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.repository.ProfessionalProfileRepository;
import dev.juanv.decconnectcode.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfessionalProfileRepository profileRepository;
    private final ProfessionalRepository professionalRepository;

    public ProfileService(ProfessionalProfileRepository profileRepository, ProfessionalRepository professionalRepository) {
        this.profileRepository = profileRepository;
        this.professionalRepository = professionalRepository;
    }


    public Optional<ProfessionalProfile> findProfile(Long professionalId){
        return profileRepository.findByProfessionalId(professionalId);
    }

    public ProfessionalProfile saveProfile (Long professionalId, ProfileRequest request){

        Professional professional = professionalRepository.findById(professionalId).orElseThrow(()-> new IllegalArgumentException("Profecional no encontrado"));

        ProfessionalProfile profile = findProfile(professionalId).orElseGet(()-> {
            ProfessionalProfile newProfile = new ProfessionalProfile();
            newProfile.setProfessional(professional);
            return newProfile;
        });

        profile.setHeadline(request.getHeadline().trim());
        profile.setDescription(request.getDescription().trim());
        profile.setExperience(request.getExperience().trim());

        String city = request.getCity();
        profile.setCity(city == null || city.isBlank() ? null : city.trim());


        return  profileRepository.save(profile);
    }



}
