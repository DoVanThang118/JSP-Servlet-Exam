package com.example.jspservlet.servlet;

import com.example.jspservlet.dao.StudentDAO;
import com.example.jspservlet.dao.impl.StudentDAOImpl;
import com.example.jspservlet.entity.Student;
import com.gargoylesoftware.htmlunit.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@WebServlet({
        "/student/index",
        "/student/index/*",
        "/student/add",
        "/student/create",
        "/student/edit",
        "/student/edit/*",
        "/student/update",
        "/student/delete",
        "/student/update/*",
        "/student/delete/*",
        "/student/search/*",
        "/student/search",
})
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private int totalPages;

    public void init() {
        studentDAO = new StudentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURL().toString();
        if (url.contains("create")){
            create(req, resp);
        }
        else if (url.contains("add")) {
            add(req, resp);
        }
        else if (url.contains("edit")) {
            try {
                edit(req, resp);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else if (url.contains("update")) {
            update(req, resp);
        }
        else if (url.contains("delete")) {
            delete(req, resp);
        }
        else if (url.contains("search")) {
            search(req, resp);
        }
        else {
            getAll(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void getAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Khoi tao danh sach du lieu
        List<Student> studentList = studentDAO.findAll();
//        Collections.sort(studentList, new Comparator<Student>() {
//            @Override
//            public int compare(Student s1, Student s2) {
//                // Sắp xếp theo ID từ lớn đến nhỏ
//                return Integer.compare(s2.getId(), s1.getId());
//            }
//        });
        studentList.sort((s1, s2) -> Integer.compare(s2.getId(), s1.getId()));

        // Tinh toan so luong trang va phan trang
        int totalItems = studentList.size();
        int itemsPerPage = 3;
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);


        // Lay tham so trang hien tai tu request
        String pageStr = req.getParameter("page");
        int currentPage = (pageStr == null) ? 1 : Integer.parseInt(pageStr);

        // Tinh vi tri bat dau va ket thuc cua list item
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        // Chia nho danh sach item theo trang hien tai
        List<Student> students = studentList.subList(startIndex, endIndex);

        // Đặt danh sách phân trang và các thông số cần thiết vào request
        req.setAttribute("students", students);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        studentDAO.remove(id);
        resp.sendRedirect("/student/index");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException {
        Student student = studentDAO.findId(Integer.parseInt(req.getParameter("id")));
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(String.valueOf(student.getBirthday()));

        req.setAttribute("date", date);
        req.setAttribute("existingStudent", student);
        req.getRequestDispatcher("/studentForm.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Student student = new Student();
            student.setId(Integer.parseInt(req.getParameter("id")));
            student.setName(req.getParameter("name"));
            student.setCode(req.getParameter("code"));
            String birthday = req.getParameter("birthday");
            student.setBirthday(dateFormat.parse(birthday));
            studentDAO.save(student);
            resp.sendRedirect("/student/index");
        } catch (ParseException |IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/studentForm.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Student student = new Student();
            student.setName(req.getParameter("name"));
            student.setCode(req.getParameter("code"));
            String birthday = req.getParameter("birthday");
            student.setBirthday(dateFormat.parse(birthday));
            studentDAO.save(student);
            resp.sendRedirect("/student/index");
        } catch (ParseException | IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Student> studentList = studentDAO.findByKeyWord(req.getParameter("keyword"), req.getParameter("code"));
        studentList.sort((s1, s2) -> Integer.compare(s2.getId(), s1.getId()));

        // Tinh toan so luong trang va phan trang
        int totalItems = studentList.size();
        int itemsPerPage = 3;
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);


        // Lay tham so trang hien tai tu request
        String pageStr = req.getParameter("page");
        int currentPage = (pageStr == null) ? 1 : Integer.parseInt(pageStr);

        // Tinh vi tri bat dau va ket thuc cua list item
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        // Chia nho danh sach item theo trang hien tai
        List<Student> students = studentList.subList(startIndex, endIndex);

        // Đặt danh sách phân trang và các thông số cần thiết vào request
        req.setAttribute("students", students);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
