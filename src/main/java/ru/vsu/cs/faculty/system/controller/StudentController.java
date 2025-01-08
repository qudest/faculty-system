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
import ru.vsu.cs.faculty.system.dto.StudentCreationDto;
import ru.vsu.cs.faculty.system.storage.entity.Student;
import ru.vsu.cs.faculty.system.storage.repository.DepartmentRepository;
import ru.vsu.cs.faculty.system.storage.repository.FacultyRepository;
import ru.vsu.cs.faculty.system.storage.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public StudentController(StudentRepository studentRepository, FacultyRepository facultyRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping({"", "/"})
    public String getAllStudents(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(required = false) String searchValue,
                                 @RequestParam(required = false) String searchColumn
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Student> studentPage = studentRepository.findAllWithFilters(searchValue, searchColumn, pageable);
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("totalItems", studentPage.getTotalElements());
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchColumn", searchColumn);
        return "students/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("student", new StudentCreationDto());
        model.addAttribute("faculties", facultyRepository.findAll());
        return "students/creationPage";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("student") StudentCreationDto studentCreationDto, BindingResult result, Model model) {
        if (studentRepository.existsByEmail(studentCreationDto.getEmail())) {
            result.rejectValue("email", "email", "Такой email уже существует");
        }
        if (studentRepository.existsByPhone(studentCreationDto.getPhone())) {
            result.rejectValue("phone", "phone", "Такой телефон уже существует");
        }

        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyRepository.findAll());
            return "students/creationPage";
        }

        Student student = new Student(studentCreationDto);
        student.setDepartment(departmentRepository.findById(studentCreationDto.getDepartmentId()).get());
        studentRepository.save(student);

        return "redirect:/students";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam Long id) {
        Student original = studentRepository.findById(id).get();
        model.addAttribute("original", original);
        StudentCreationDto edited = new StudentCreationDto(original);
        model.addAttribute("edited", edited);
        model.addAttribute("faculties", facultyRepository.findAll());
        return "students/editPage";
    }

    @PostMapping("/edit")
    public String editStudent(Model model, @RequestParam Long id, @Valid @ModelAttribute("edited") StudentCreationDto studentCreationDto, BindingResult result) {
        try {
            Student original = studentRepository.findById(id).get();
            model.addAttribute("original", original);
            model.addAttribute("faculties", facultyRepository.findAll());

            if (!studentCreationDto.getEmail().equals(original.getEmail()) && studentRepository.existsByEmail(studentCreationDto.getEmail())) {
                result.rejectValue("email", "email", "Такой email уже существует");
            }
            if (!studentCreationDto.getPhone().equals(original.getPhone()) && studentRepository.existsByPhone(studentCreationDto.getPhone())) {
                result.rejectValue("phone", "phone", "Такой телефон уже существует");
            }

            if (result.hasErrors()) {
                return "students/editPage";
            }
        } catch (Exception e) {
            return "redirect:/students";
        }

        Student edited = new Student(studentCreationDto);
        edited.setId(id);
        edited.setDepartment(departmentRepository.findById(studentCreationDto.getDepartmentId()).get());
        studentRepository.save(edited);

        return "redirect:/students";
    }

    @GetMapping("/delete")
    public String deleteStudent(@RequestParam Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

}
