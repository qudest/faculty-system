package ru.vsu.cs.faculty.system.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.faculty.system.dto.DepartmentCreationDto;
import ru.vsu.cs.faculty.system.dto.FacultyCreationDto;
import ru.vsu.cs.faculty.system.storage.entity.Department;
import ru.vsu.cs.faculty.system.storage.entity.Faculty;
import ru.vsu.cs.faculty.system.storage.repository.DepartmentRepository;
import ru.vsu.cs.faculty.system.storage.repository.FacultyRepository;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    public DepartmentController(DepartmentRepository departmentRepository, FacultyRepository facultyRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping({"", "/"})
    public String getAllDepartments(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    @RequestParam(required = false) String searchValue,
                                    @RequestParam(required = false) String searchColumn
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Department> departmentPage = departmentRepository.findAllWithFilters(searchValue, searchColumn, pageable);
        model.addAttribute("departments", departmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("totalItems", departmentPage.getTotalElements());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchColumn", searchColumn);
        return "departments/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("department", new DepartmentCreationDto());
        model.addAttribute("faculties", facultyRepository.findAll());
        return "departments/creationPage";
    }

    @PostMapping("/create")
    public String createDepartment(@Valid @ModelAttribute("department") DepartmentCreationDto departmentCreationDto, BindingResult result, Model model) {
        if (departmentRepository.existsByEmail(departmentCreationDto.getEmail())) {
            result.rejectValue("email", "email", "Такой email уже существует");
        }
        if (departmentRepository.existsByPhone(departmentCreationDto.getPhone())) {
            result.rejectValue("phone", "phone", "Такой телефон уже существует");
        }

        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyRepository.findAll());
            return "departments/creationPage";
        }

        Department department = new Department(departmentCreationDto);
        department.setFaculty(facultyRepository.findById(departmentCreationDto.getFacultyId()).get());
        departmentRepository.save(department);

        return "redirect:/departments";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        Department original = departmentRepository.findById(id).get();
        model.addAttribute("original", original);
        DepartmentCreationDto edited = new DepartmentCreationDto(original);
        model.addAttribute("edited", edited);
        model.addAttribute("faculties", facultyRepository.findAll());
        return "departments/editPage";
    }

    @PostMapping("/edit")
    public String editStudent(Model model, @RequestParam Long id, @Valid @ModelAttribute("edited") DepartmentCreationDto departmentCreationDto, BindingResult result) {
        try {
            Department original = departmentRepository.findById(id).get();
            model.addAttribute("original", original);
            model.addAttribute("faculties", facultyRepository.findAll());

            if (!departmentCreationDto.getEmail().equals(original.getEmail()) && departmentRepository.existsByEmail(departmentCreationDto.getEmail())) {
                result.rejectValue("email", "email", "Такой email уже существует");
            }
            if (!departmentCreationDto.getPhone().equals(original.getPhone()) && departmentRepository.existsByPhone(departmentCreationDto.getPhone())) {
                result.rejectValue("phone", "phone", "Такой телефон уже существует");
            }

            if (result.hasErrors()) {
                return "departments/editPage";
            }
        } catch (Exception e) {
            return "redirect:/departments";
        }

        Department edited = new Department(departmentCreationDto);
        edited.setId(id);
        edited.setFaculty(facultyRepository.findById(departmentCreationDto.getFacultyId()).get());
        departmentRepository.save(edited);

        return "redirect:/departments";
    }

    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/departments";
    }
}
