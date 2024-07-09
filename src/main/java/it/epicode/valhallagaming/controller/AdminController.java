package it.epicode.valhallagaming.controller;

import it.epicode.valhallagaming.dto.adminDTO.AdminCreateRequest;
import it.epicode.valhallagaming.dto.adminDTO.AdminDeleteResponse;
import it.epicode.valhallagaming.dto.adminDTO.AdminEditRequest;
import it.epicode.valhallagaming.dto.adminDTO.AdminResponse;
import it.epicode.valhallagaming.entity.Admin;
import it.epicode.valhallagaming.entity.Role;
import it.epicode.valhallagaming.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<AdminResponse> getAllAdmins() {
        return adminService.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AdminResponse getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.findById(id);
        if (admin.isPresent()) {
            return convertToDTO(admin.get());
        } else {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
    }

    @PostMapping
    public AdminResponse createAdmin(@RequestBody AdminCreateRequest adminCreateRequest) {
        Admin admin = convertToEntity(adminCreateRequest);
        admin.setRole(Role.ADMIN);
        Admin savedAdmin = adminService.registerAdmin(admin);
        return convertToDTO(savedAdmin);
    }

    @PutMapping("/{id}")
    public AdminResponse editAdmin(@PathVariable Long id, @RequestBody AdminEditRequest adminEditRequest) {
        Admin admin = convertToEntity(adminEditRequest);
        admin.setId(id);
        Admin updatedAdmin = adminService.registerAdmin(admin);
        return convertToDTO(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public AdminDeleteResponse deleteAdmin(@PathVariable Long id) {
        adminService.deleteById(id);
        AdminDeleteResponse response = new AdminDeleteResponse();
        response.setId(id);
        response.setMessage("Admin eliminato");
        return response;
    }

    @PostMapping("/collab")
    public AdminResponse createCollaborator(@RequestBody AdminCreateRequest adminCreateRequest) {
        if (adminCreateRequest.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Solo gli admin possono creare collaboratori");
        }

        Admin admin = convertToEntity(adminCreateRequest);
        admin.setRole(Role.COLLABORATOR);
        Admin savedCollaborator = adminService.registerAdmin(admin);
        return convertToDTO(savedCollaborator);
    }

    private AdminResponse convertToDTO(Admin admin) {
        AdminResponse dto = new AdminResponse();
        dto.setId(admin.getId());
        dto.setFirstName(admin.getFirstName());
        dto.setLastName(admin.getLastName());
        dto.setUserName(admin.getUserName());
        dto.setEmail(admin.getEmail());
        dto.setLoggedin(admin.isLoggedin());
        dto.setRole(admin.getRole());
        return dto;
    }

    private Admin convertToEntity(AdminCreateRequest dto) {
        return new Admin(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getEmail(), dto.getPassword(), dto.isLoggedin(), dto.getRole());
    }

    private Admin convertToEntity(AdminEditRequest dto) {
        return new Admin(dto.getFirstName(), dto.getLastName(), dto.getUserName(), dto.getEmail(), dto.getPassword(), dto.isLoggedin(), dto.getRole());
    }

    @GetMapping("/collabs")
    public List<Admin> getAllCollaborators() {
        return adminService.findAllCollaborators();
    }

    @PutMapping("/collab/{id}")
    public Admin updateCollaborator(@PathVariable Long id, @RequestBody Admin admin) {
        admin.setId(id);
        return adminService.saveCollaborator(admin);
    }

}
