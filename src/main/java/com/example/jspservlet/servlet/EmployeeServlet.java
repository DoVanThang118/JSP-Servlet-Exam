package com.example.jspservlet.servlet;

import com.example.jspservlet.dao.EmployeeDAO;
import com.example.jspservlet.dao.impl.EmployeeDAOImpl;
import com.example.jspservlet.entity.Employee;
import com.example.jspservlet.entity.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet({
        "/employee/index",
        "/employee/search",
        "/employee/search/*",
        "/employee/add",
        "/employee/create",
        "/employee/edit",
        "/employee/edit/*",
        "/employee/update",
        "/employee/update/*",
        "/employee/delete",
        "/employee/delete/*",
})
public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;

    public void init() {
        employeeDAO = new EmployeeDAOImpl();
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
        List<Employee> employeeList = employeeDAO.findAll();
        employeeList.sort((s1, s2) -> Integer.compare(s2.getId(), s1.getId()));
        req.setAttribute("employeeList", employeeList);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        employeeDAO.remove(id);
        resp.sendRedirect("/employee/index");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ParseException {
        Employee employee = employeeDAO.findId(Integer.parseInt(req.getParameter("id")));
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(String.valueOf(employee.getBirthday()));

        req.setAttribute("date", date);
        req.setAttribute("employee", employee);
        req.getRequestDispatcher("/employee.jsp").forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(req.getParameter("id")));
            employee.setFullname(req.getParameter("fullname"));
            String birthday = req.getParameter("birthday");
            employee.setBirthday(dateFormat.parse(birthday));
            employee.setAddress(req.getParameter("address"));
            employee.setPosition(req.getParameter("position"));
            employee.setDepartment(req.getParameter("department"));
            employeeDAO.save(employee);
            resp.sendRedirect("/employee/index");
        } catch (ParseException |IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/employee.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Employee employee = new Employee();
            employee.setFullname(req.getParameter("fullname"));
            String birthday = req.getParameter("birthday");
            employee.setBirthday(dateFormat.parse(birthday));
            employee.setAddress(req.getParameter("address"));
            employee.setPosition(req.getParameter("position"));
            employee.setDepartment(req.getParameter("department"));
            employeeDAO.save(employee);
            resp.sendRedirect("/employee/index");
        } catch (ParseException | IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Employee> employeeList = employeeDAO.findByKeyWord(
                req.getParameter("fullname"),
                req.getParameter("address"),
                req.getParameter("position"),
                req.getParameter("department"));
        employeeList.sort((s1, s2) -> Integer.compare(s2.getId(), s1.getId()));

        // Đặt danh sách phân trang và các thông số cần thiết vào request
        req.setAttribute("employeeList", employeeList);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }
}
