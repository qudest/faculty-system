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
import ru.vsu.cs.faculty.system.dto.FacultyCreationDto;
import ru.vsu.cs.faculty.system.storage.entity.Department;
import ru.vsu.cs.faculty.system.storage.entity.Faculty;
import ru.vsu.cs.faculty.system.storage.repository.DepartmentRepository;
import ru.vsu.cs.faculty.system.storage.repository.FacultyRepository;

import java.util.List;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public FacultyController(FacultyRepository facultyRepository, DepartmentRepository departmentRepository) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping({"", "/"})
    public String getAllFaculties(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(required = false) String searchValue,
                                  @RequestParam(required = false) String searchColumn
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Faculty> facultyPage = facultyRepository.findAllWithFilters(searchValue, searchColumn, pageable);
        model.addAttribute("faculties", facultyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", facultyPage.getTotalPages());
        model.addAttribute("totalItems", facultyPage.getTotalElements());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchColumn", searchColumn);
        return "faculties/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("faculty", new FacultyCreationDto());
        return "faculties/creationPage";
    }

    @PostMapping("/create")
    public String createFaculty(@Valid @ModelAttribute("faculty") FacultyCreationDto faculty, BindingResult result) {
        if (facultyRepository.existsByEmail(faculty.getEmail())) {
            result.rejectValue("email", "email", "Такой email уже существует");
        }
        if (facultyRepository.existsByPhone(faculty.getPhone())) {
            result.rejectValue("phone", "phone", "Такой телефон уже существует");
        }

        if (result.hasErrors()) {
            return "faculties/creationPage";
        }

        facultyRepository.save(new Faculty(faculty));

        return "redirect:/faculties";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        Faculty original = facultyRepository.findById(id).get();
        model.addAttribute("original", original);
        FacultyCreationDto edited = new FacultyCreationDto(original);
        model.addAttribute("edited", edited);
        return "faculties/editPage";
    }

    @PostMapping("/edit")
    public String editFaculty(Model model, @RequestParam Long id, @Valid @ModelAttribute("edited") FacultyCreationDto faculty, BindingResult result) {
        try {
            Faculty original = facultyRepository.findById(id).get();
            model.addAttribute("original", original);

            if (!faculty.getEmail().equals(original.getEmail()) && facultyRepository.existsByEmail(faculty.getEmail())) {
                result.rejectValue("email", "email", "Такой email уже существует");
            }
            if (!faculty.getPhone().equals(original.getPhone()) && facultyRepository.existsByPhone(faculty.getPhone())) {
                result.rejectValue("phone", "phone", "Такой телефон уже существует");
            }

            if (result.hasErrors()) {
                return "faculties/editPage";
            }
        } catch (Exception e) {
            return "redirect:/faculties";
        }

        Faculty edited = new Faculty(faculty);
        edited.setId(id);
        facultyRepository.save(edited);

        return "redirect:/faculties";
    }

    @GetMapping("/delete")
    public String deleteFaculty(@RequestParam Long id) {
        facultyRepository.deleteById(id);
        return "redirect:/faculties";
    }

    @GetMapping("/{id}/departments")
    @ResponseBody
    public List<Department> getSpecialitiesByFacultyId(@PathVariable Long id) {
        return departmentRepository.findAllByFaculty_Id((id));
    }

}
