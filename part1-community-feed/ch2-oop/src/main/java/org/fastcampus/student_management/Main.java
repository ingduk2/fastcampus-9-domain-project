package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;
import org.fastcampus.student_management.ui.UserInputType;

public class Main {

    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();

        StudentService studentService = new StudentService(studentRepository);
        CourseService courseService = new CourseService(courseRepository, studentService);

        CoursePresenter coursePresenter = new CoursePresenter();
        StudentPresenter studentPresenter = new StudentPresenter();

        initData(studentService, courseService);

        CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
        StudentController studentController = new StudentController(studentPresenter, studentService);

        studentPresenter.showMenu();
        UserInputType userInputType = studentController.getUserInput();
        while (userInputType != UserInputType.EXIT) {
            switch (userInputType) {
                case NEW_STUDENT:
                    studentController.registerStudent();
                    break;
                case NEW_COURSE:
                    courseController.registerCourse();
                    break;
                case SHOW_COURSE_DAY_OF_WEEK:
                    courseController.showCourseDayOfWeek();
                    break;
                case ACTIVATE_STUDENT:
                    studentController.activateStudent();
                    break;
                case DEACTIVATE_STUDENT:
                    studentController.deactivateStudent();
                    break;
                case CHANGE_FEE:
                    courseController.changeFee();
                    break;
                default:
                    studentPresenter.showErrorMessage();
                    break;
            }
            studentPresenter.showMenu();
            userInputType = studentController.getUserInput();
        }
    }

    private static void initData(StudentService studentService, CourseService courseService) {
        StudentInfoDto student1 = new StudentInfoDto("a", 20, "서울시 강남구");
        StudentInfoDto student2 = new StudentInfoDto("b", 21, "서울시 노원구");
        studentService.saveStudent(student1);
        studentService.saveStudent(student2);

        CourseInfoDto course1 = new CourseInfoDto("바이올린", 1000, "SATURDAY", "a", 171729908L);
        CourseInfoDto course2 = new CourseInfoDto("첼로", 1200, "SATURDAY", "b", 171729908L);
        courseService.registerCourse(course1);
        courseService.registerCourse(course2);
    }
}