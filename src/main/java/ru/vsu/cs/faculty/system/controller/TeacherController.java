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
import ru.vsu.cs.faculty.system.dto.TeacherCreationDto;
import ru.vsu.cs.faculty.system.storage.entity.Teacher;
import ru.vsu.cs.faculty.system.storage.repository.DepartmentRepository;
import ru.vsu.cs.faculty.system.storage.repository.FacultyRepository;
import ru.vsu.cs.faculty.system.storage.repository.TeacherRepository;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherController(TeacherRepository teacherRepository, FacultyRepository facultyRepository, DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping({"", "/"})
    public String getAllTeachers(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(required = false) String searchValue,
                                 @RequestParam(required = false) String searchColumn
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Teacher> teacherPage = teacherRepository.findAllWithFilters(searchValue, searchColumn, pageable);
        model.addAttribute("teachers", teacherPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", teacherPage.getTotalPages());
        model.addAttribute("totalItems", teacherPage.getTotalElements());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchColumn", searchColumn);
        return "teachers/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("teacher", new TeacherCreationDto());
        model.addAttribute("faculties", facultyRepository.findAll());
        return "teachers/creationPage";
    }

    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute("teacher") TeacherCreationDto teacherCreationDto, BindingResult result, Model model) {
        if (teacherRepository.existsByEmail(teacherCreationDto.getEmail())) {
            result.rejectValue("email", "email", "Такой email уже существует");
        }
        if (teacherRepository.existsByPhone(teacherCreationDto.getPhone())) {
            result.rejectValue("phone", "phone", "Такой телефон уже существует");
        }

        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyRepository.findAll());
            return "teachers/creationPage";
        }

        Teacher teacher = new Teacher(teacherCreationDto);
        teacher.setDepartment(departmentRepository.findById(teacherCreationDto.getDepartmentId()).get());
        teacherRepository.save(teacher);

        return "redirect:/teachers";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        Teacher original = teacherRepository.findById(id).get();
        model.addAttribute("original", original);
        TeacherCreationDto edited = new TeacherCreationDto(original);
        model.addAttribute("edited", edited);
        model.addAttribute("faculties", facultyRepository.findAll());
        return "teachers/editPage";
    }

    @PostMapping("/edit")
    public String editTeacher(Model model, @RequestParam Long id, @Valid @ModelAttribute("edited") TeacherCreationDto teacherCreationDto, BindingResult result) {
        try {
            Teacher original = teacherRepository.findById(id).get();
            model.addAttribute("original", original);
            model.addAttribute("faculties", facultyRepository.findAll());

            if (!teacherCreationDto.getEmail().equals(original.getEmail()) && teacherRepository.existsByEmail(teacherCreationDto.getEmail())) {
                result.rejectValue("email", "email", "Такой email уже существует");
            }
            if (!teacherCreationDto.getPhone().equals(original.getPhone()) && teacherRepository.existsByPhone(teacherCreationDto.getPhone())) {
                result.rejectValue("phone", "phone", "Такой телефон уже существует");
            }

            if (result.hasErrors()) {
                return "teachers/editPage";
            }
        } catch (Exception e) {
            return "redirect:/teachers";
        }

        Teacher edited = new Teacher(teacherCreationDto);
        edited.setId(id);
        edited.setDepartment(departmentRepository.findById(teacherCreationDto.getDepartmentId()).get());
        teacherRepository.save(edited);

        return "redirect:/teachers";
    }

    @GetMapping("/delete")
    public String deleteTeacher(@RequestParam Long id) {
        teacherRepository.deleteById(id);
        return "redirect:/teachers";
    }

}
