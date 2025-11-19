package com.example.VanishChat.Services;

import com.example.VanishChat.Model.Registration;
import com.example.VanishChat.Repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public String registerUser(String username, String email, String password,
                               String businessName, String gstNumber,
                               String address, MultipartFile file) throws IOException {

        // Log start
        System.out.println("— registerUser called for: " + username);

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Upload dir created: " + dir.getAbsolutePath());
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = uploadDir + File.separator + filename;

        file.transferTo(new File(filePath));
        System.out.println("File saved to: " + filePath);

        Registration reg = new Registration();
        reg.setUsername(username);
        reg.setEmail(email);
        reg.setPassword(password);
        reg.setBusinessName(businessName);
        reg.setGstNumber(gstNumber);
        reg.setAddress(address);
        reg.setProofFilePath(filePath);

        registrationRepository.save(reg);
        System.out.println("Saved registration entity with id: " + reg.getId());

        return "Registration successful!";
    }
}
